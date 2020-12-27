package doug.spring.bootJpaDemo.controller;

import java.io.IOException;
import java.io.PrintWriter;    					// added for writing response data

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;    			// added for Cookie

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;					// added for JSON (Gson)
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;	
import com.google.gson.JsonParser;	
import com.google.gson.JsonParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import doug.spring.bootJpaDemo.model.*;
import doug.spring.bootJpaDemo.service.*;

@Component
@WebServlet(description = "Servlet for Spring Data JPA project", urlPatterns = { "/SbJpaServlet" })
public class SbJpaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String today;
    static final float TAX_RATE = 0.0785f;	// temporary for the sales tax calculation

	class Customer2 {
		Customer customer;
		String emplName;
		public Customer2 (String emplName) {
			this.customer = new Customer();
			this.emplName = emplName;
		}
	}
	class Order2 {
		Order  order;
    	int    itemCnt;
    	float  subTotal;	
        float  taxAmt;
    	String custName;	// JOIN (customers)
    	String emplName;	// JOIN (employees)
    	public Order2 () {
    		this.order = new Order();
    		this.itemCnt = 0;
    		this.subTotal = 0.0f;
    		this.taxAmt   = 0.0f;
    		this.custName = "";
    		this.emplName = "";
    	}
    }
	class OrderItem2 {		
		OrderItem orderItem;
		String    itemName; // JOIN (items)
		public OrderItem2() {
			this.orderItem = new OrderItem();
		}
    }
    class OneOrder {		// Order + OrderItems[itemCnt]
    	Order2 ord;
    	OrderItem2[] ordItem;
    	public OneOrder(int count) {
    		this.ord = new Order2();
    		if (count > 0) {
    			this.ordItem = new OrderItem2[count];	// reference to objects
    			for (int i = 0; i < count; i++) this.ordItem[i] = new OrderItem2();
    		}
    		this.ord.itemCnt = count;
    	}
    }
    Customer2  c2		= null;
    Order	   order    = null;
    Order2     o2       = null;
    OrderItem  orderItem= null;
    OrderItem2 oi2		= null;
    OneOrder   oneOrder = null;
    CustOrder  custOrder= null;
    String requestData  = null;	// request data (JSON string)
    String responseData = null;	// response data (JSON string)
    String whatEvent    = null;
    String userName     = null;
    
/*  @Autowired UserService userService;
    @Autowired CustomerService customerService;
    @Autowired EmployeeService employeeService;
    @Autowired ItemService itemService;
    @Autowired OrderService orderService;
    @Autowired OrderItemService orderItemService; */
    private static UserService userService;
    private static CustomerService customerService;
    private static EmployeeService employeeService;
    private static ItemService itemService;
    private static OrderService orderService;
    private static OrderItemService orderItemService;

    public SbJpaServlet() {
    	super();  	
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        today = dateFormat.format(date);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    userService 	= (UserService) 	BeanUtils.getBean("userService");
	    customerService = (CustomerService) BeanUtils.getBean("customerService");
	    employeeService = (EmployeeService) BeanUtils.getBean("employeeService");
	    itemService 	= (ItemService) 	BeanUtils.getBean("itemService");
	    orderService 	= (OrderService) 	BeanUtils.getBean("orderService");
	    orderItemService= (OrderItemService)BeanUtils.getBean("orderItemService");
    	System.out.println("init() of SbJpaServlet");
    }

    @Override
	public void destroy() {
    	System.out.println("destroy() of SbJpaServlet");
	}
    
//  Handles MySQL database through Spring Data JPA (service)
	protected int authenticationCheck(String userId, String password) {
	    int status = 0;    // 0: user does not exist, 1: password not match, 2: inactive user, 3: active user	       
	    Optional<User> user = userService.findById(userId);
    	if (user.isPresent()) {
    		status++;
    		if (user.get().getPassword().equals(password)) {
    			status++;
    			if (user.get().isActive()) status++;
    		}
    		userName = user.get().getName();
    	}
    	return status;
    }

	protected boolean readOrder(int orderId) {		
		Optional<Order> o = orderService.findById(orderId);
		if (!o.isPresent()) return false;
		List<OrderItem> oi = orderItemService.findByOrderId(orderId);
		int count = oi.size();
		oneOrder = new OneOrder(count);
		oneOrder.ord.order    = o.get();
		oneOrder.ord.itemCnt  = count;
		Optional<Customer> c  = customerService.findById(o.get().getCustId());
		oneOrder.ord.custName = c.get().getName();
		Optional<Employee> e  = employeeService.findById(o.get().getEmplId());
		oneOrder.ord.emplName = e.get().getEmplName();
		float subTotal = 0.0f;
		for (int i = 0; i < count; i++) {
			oneOrder.ordItem[i].orderItem = oi.get(i);
			subTotal += oi.get(i).getUPrice() * oi.get(i).getQty();
			Optional<Item> item = itemService.findById(oi.get(i).getItemId());
			oneOrder.ordItem[i].itemName = item.get().getName();	
		}
		oneOrder.ord.subTotal = subTotal;
		oneOrder.ord.taxAmt = (o.get().isTaxExmt()) ? 0.0f : (subTotal * TAX_RATE);
	    return true;
	}
	
	protected boolean readCustOrders(int custId, int maxCnt) {
		List<IdAndDate> iad = orderService.findByCustId(custId);
		int count = iad.size();
		if (count == 0) return false;
		if (count > maxCnt) count = maxCnt;
		custOrder = new CustOrder(custId, count);
		for (int i = 0; i < count; i++) custOrder.cOrds[i] = iad.get(i);
		return true;
	}

	protected boolean readCust(int custId) {
		Optional<Customer> c = customerService.findById(custId);
		if (!c.isPresent()) return false;
		Optional<Employee> e = employeeService.findById(c.get().getEmplId());
		if (!e.isPresent()) return false;
		c2 = new Customer2(e.get().getEmplName());
		c2.customer  = c.get();
	    return true;
	}
	
	protected boolean readEmpl(int emplId) {
		Optional<Employee> e = employeeService.findById(emplId);
		if (!e.isPresent()) return false;
    	responseData = "{\"emplName\":\""+e.get().getEmplName()+"\"}";
	    return true;
	}
	
	protected boolean readItem(int itemId) {
		Optional<Item> i = itemService.findById(itemId);
		if (!i.isPresent()) return false;
    	responseData = "{\"itemName\":\""+i.get().getName()+"\"}";
    	return true;
	}

	protected boolean insertOrder(Order o) {    
        o.setOrderId(0); 	// auto-increment
		o.setCrName(userName);
        o.setCrDt(today);
        o = orderService.save(o);
	    return true;
	}

	protected boolean updateOrder(Order o) {
		o.setUpdName(userName);
		o.setUpdDt(today);
		switch (whatEvent) {
			case "07" :	// 'Change Order' clicked
			case "09" :	// 'Discount' changed				
				orderService.updateById(o.getOrderId(), o);
			    break;
			case "08" :	// 'Cancel Order' clicked
				orderService.cancelOrder(o.getOrderId());
				break;
			default:	// just in case
				return false;
	    }
	    return true;
	}

	protected boolean readOrderItem(int orderId, int seq) {
		Optional<OrderItem> oi = orderItemService.findByPk(orderId, seq);
		if (!oi.isPresent()) return false;
		oi2 = new OrderItem2();
		oi2.orderItem = oi.get();
		Optional<Item> i = itemService.findById(oi.get().getItemId());
		if (!i.isPresent()) return false;
		oi2.itemName = i.get().getName();
	    return true;
	}

	protected boolean insertOrderItem(OrderItem oi) {    
		List<OrderItem> oItems = orderItemService.findByOrderId(oi.getOrderId());
		int count = oItems.size();
		int seq = 0;
		if (count != 0) seq = oItems.get(count - 1).getSeq();
		oi.setSeq(seq + 1);
		oi.setUpdDt(today);
        oi = orderItemService.save(oi);
	    return true;
	}

	protected boolean updateOrderItem(OrderItem oi) {    
		int orderId = oi.getOrderId();
		int seq		= oi.getSeq();
		if (whatEvent.equals("16")) {	// 'Change Item' clicked
        	oi.setUpdDt(today);
        	orderItemService.updateByPk(orderId, seq, oi);
        } else {						// 'Delete Item' clicked
        	orderItemService.deleteByPk(orderId, seq);
        }
	    return true;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		System.out.println("doGet() of SbJpaServlet");		        
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String wellDone = "N";		// for cookie "wellDone"
		int orderId = 0;
		int custId  = 0;
//		System.out.println("doPost() of SbJpaServlet");		        
		try {
			requestData = request.getParameter("json");
			System.out.println("request data is " + requestData);
			// prepare for the data parsing (common routine)
			@SuppressWarnings("deprecation")
			JsonParser parser = new JsonParser();
			@SuppressWarnings("deprecation")
			JsonElement element = parser.parse(requestData);		
			JsonObject json = new JsonObject();
			Gson gson = new Gson();	
			// get whatEvent & userName value from cookie
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie:cookies) {
				if (cookie.getName().equals("whatEvent")) whatEvent= cookie.getValue();
				if (cookie.getName().equals("userName")) {
					String uName = cookie.getValue();
					userName = uName.replace('_', ' ');
				}
			}
			switch (whatEvent) {
				case "00": 	// Authenticate a user				
					json = (JsonObject) element;
					String userId = json.getAsJsonObject().get("userId").getAsString();
					String passwd = json.getAsJsonObject().get("passwd").getAsString();
					int status = authenticationCheck(userId, passwd);					
					String path = request.getContextPath();		
					String strStatus = Integer.toString(status);
					Cookie cookie = new Cookie("authStat", strStatus);
					cookie.setPath(path);   // set cookie's usage range
					cookie.setMaxAge(-1);	// do not delete until browser finishes
					response.addCookie(cookie);
					if (status == 3) {
						String name = userName.replace(' ',  '_');    // Cookie value should not include space
						cookie = new Cookie("userName", name);
						cookie.setPath(path);
						cookie.setMaxAge(-1);
						response.addCookie(cookie);
					}
					responseData = "{\"Message\":\"Done\"}";
					wellDone = "Y";
					break;
				case "01":	// 'Order #' inserted (order & orderItems inquiry)
					orderId = element.getAsJsonObject().get("orderId").getAsInt();
					if (readOrder(orderId)) {
						gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();			
						element = gson.toJsonTree(oneOrder);					
						json = (JsonObject) element;
						responseData = json.toString();
						wellDone = "Y";
					}
					break;
				case "03" :	// 'Customer #' inserted
					custId = element.getAsJsonObject().get("custId").getAsInt();
					if (readCust(custId)) {
						gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();			
						element = gson.toJsonTree(c2);
						json = (JsonObject) element;		
						responseData = json.toString();
						wellDone = "Y";
					}
					break;
				case "04" :	// 'Recent Orders' clicked
					custId = element.getAsJsonObject().get("custId").getAsInt();
					int maxCnt = element.getAsJsonObject().get("maxCnt").getAsInt();
					if (readCustOrders(custId, maxCnt)) {
						gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();			
						element = gson.toJsonTree(custOrder);
						json = (JsonObject) element;		
						responseData = json.toString();
						wellDone = "Y";
					}
					break;
				case "05" :	// 'Sales Rep.' inserted
					int emplId = element.getAsJsonObject().get("emplId").getAsInt();
					if (readEmpl(emplId)) wellDone = "Y";
					break;
				case "06" :	// 'Register Order' clicked
					json = (JsonObject) element;
					order = new Order();
					//
					order = gson.fromJson(json, Order.class);
					if (insertOrder(order)) {
						responseData = "{\"orderId\":"+order.getOrderId()+",";
						responseData+= "\"crName\":\""+order.getCrName()+"\",";
						responseData+= "\"crDt\":\""+order.getCrDt()+"\"}";
						wellDone = "Y";
					}
					break;
				case "07" :	// 'Change Order' clicked				
				case "08" :	// 'Cancel Order' clicked
				case "09" :	// 'Discount' changed					
					json = (JsonObject) element;
					order = new Order();
					order = gson.fromJson(json, Order.class);
					if (updateOrder(order)) {
						responseData = "{\"updName\":\""+order.getUpdName()+"\",";
						responseData+= "\"updDt\":\""+order.getUpdDt()+"\"}";
						wellDone = "Y";
					}
					break;
				case "12" :	// 'Item #' inserted
					int itemId = element.getAsJsonObject().get("itemId").getAsInt();
					if (readItem(itemId)) wellDone = "Y";
					break;
				case "15" :	// 'Add Item' clicked			
				case "16" :	// 'Change Item' clicked
				case "17" :	// 'Delete Item' clicked
					json = (JsonObject) element;
					orderItem = new OrderItem();
					orderItem = gson.fromJson(json, OrderItem.class);
					boolean good = whatEvent.equals("15") ? insertOrderItem(orderItem) : updateOrderItem(orderItem);
					if (good) {
						responseData = "{\"seq\":\""+orderItem.getSeq()+"\"}";
						wellDone = "Y";
					}
					break;
				default:	// just in case
					System.out.println("wrong whatEvent value: "+whatEvent);		        					
					break;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} finally {
			// write cookie which notes client about the server process is well done or not
			String path = request.getContextPath();		
			Cookie cookie = new Cookie("wellDone", wellDone);
			cookie.setPath(path);   // set cookie's usage range
			cookie.setMaxAge(-1);	// do not delete until browser finishes	
			response.addCookie(cookie);
			// write response data to send to client
			if (wellDone.equals("N")) responseData = "{\"Message\":\"Not done well\"}";
			System.out.println("response data is " + responseData);
			response.setContentType("json");
			PrintWriter writer = response.getWriter();
			writer.println(responseData);
			writer.flush();
			writer.close();
//			System.out.println("ending doPost()");
			// if "View Resolver" works instead of sending (JSON) data to client,
			// write data to "request" and forward control to JSP (not to callback function)
//			RequestDispatcher rd = request.getRequestDispatcher("/RequestJSP.jsp");    // Path which excludes context path (start with /)
//			rd.forward(request,  response);
		}
    }
}