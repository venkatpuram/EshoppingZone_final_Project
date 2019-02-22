package com.cg.eshoppingzone.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.cg.eshoppingzone.model.Address;
import com.cg.eshoppingzone.model.Cart;
import com.cg.eshoppingzone.model.Ewallet;
import com.cg.eshoppingzone.model.Items;
import com.cg.eshoppingzone.model.Orders;
import com.cg.eshoppingzone.model.Product;
import com.cg.eshoppingzone.model.Statement;
import com.cg.eshoppingzone.model.User;
import com.cg.eshoppingzone.model.UserProfile;
import com.cg.eshoppingzone.service.SecurityService;
import com.cg.eshoppingzone.service.UserService;
import com.cg.eshoppingzone.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
//////////////////////////////////////////////////////////
    
    @Autowired
	private RestTemplate restTemplate;

	private static UserProfile currentProfile = new UserProfile();

	private static UserProfile profile = new UserProfile();
	
	private static Address staticAddress = new Address();

	private Product product;


	@RequestMapping("/homepage")
	public String homePage()
	{
		return "homepage";
	}
	@RequestMapping("/profilecreation")
	public String creatingProfile(@RequestParam("mobilenumber") Long mobilenumber,
			@RequestParam("email") String emailId, @RequestParam("password") String password) {
		UserProfile profile = new UserProfile();
		profile.setMobileNumber(mobilenumber);
		profile.setEmailId(emailId);
		profile.setPassword(password);
		UserProfile newProfile = restTemplate.postForObject("http://localhost:8080/profiles/customers", profile,
				UserProfile.class);
		System.out.println(newProfile);
		restTemplate.postForEntity("http://localhost:9091/carts/" + newProfile.getProfileId(), null, Cart.class);
		restTemplate.postForEntity("http://localhost:8426/wallets/" + newProfile.getProfileId(), null, null);
		return "login";
	}

	@RequestMapping(value = "/loginmanually", method = RequestMethod.POST)
	public String profileLogin(@RequestParam("mobilenumber") Long mobilenumber,
			@RequestParam("password") String password) {
		UserProfile profile = restTemplate.getForObject("http://localhost:8080/profiles/mobile/" + mobilenumber,
				UserProfile.class);

		currentProfile.setProfileId(profile.getProfileId());
		System.out.println(currentProfile.getProfileId());
		System.out.println(profile.getPassword() + " password in if:" + password);
		System.out.println(profile.getMobileNumber() + " mobile:" + mobilenumber);
		if ((profile.getMobileNumber().equals(mobilenumber)) && (profile.getPassword().equals(password)))
			return "index";
		else
			return "login";

	}

	@RequestMapping("/viewprofile")
	public ModelAndView viewprofile()
	{
		UserProfile profile=restTemplate.getForObject("http://localhost:8080/profiles/" + currentProfile.getProfileId(),UserProfile.class);
		System.out.println("profile:"+profile.toString());
		return new ModelAndView("viewprofile","profile",profile);
	}
	
	@RequestMapping("/updateProfile")
	public ModelAndView updateprofile()
	{
		UserProfile profile=restTemplate.getForObject("http://localhost:8080/profiles/" + currentProfile.getProfileId(),UserProfile.class);
		System.out.println("profile:"+profile.toString());
		return new ModelAndView("updateProfile","profile",profile);
	}
	
	@RequestMapping("/updateDetails")
	public ModelAndView updateDetails(@RequestParam("email") String email,
			@RequestParam("mobile") Long mobilenumber,
			@RequestParam("gender") String gender)
	{
		UserProfile profile=restTemplate.getForObject("http://localhost:8080/profiles/" + currentProfile.getProfileId(),UserProfile.class);
		profile.setMobileNumber(mobilenumber);
		profile.setEmailId(email);
		profile.setGender(gender);
		restTemplate.put("http://localhost:8080/profiles",profile,UserProfile.class);
		System.out.println("profile:"+profile.toString());
		return new ModelAndView("viewprofile","profile",profile);
	}
	
	
	@RequestMapping("/yourorders")
	public ModelAndView viewOrders()
	{
		@SuppressWarnings("unchecked")
		List<Orders> orders=restTemplate.getForObject("http://localhost:9090/orders/order/" + currentProfile.getProfileId(),List.class);
		System.out.println("orders"+orders);
		return new ModelAndView("vieworders","orders",orders);
	}
	
	@RequestMapping("/yourwallet")
	public ModelAndView viewWallet()
	{
		Double balance = restTemplate.getForObject("http://localhost:8426/wallets/"+ currentProfile.getProfileId(),Double.class);
		System.out.println("walletBalance:"+balance);
		return new ModelAndView("wallet","wallet",balance);
	}
	
	@RequestMapping("/viewstatements")
	public ModelAndView viewStatements()
	{
		Double balance = restTemplate.getForObject("http://localhost:8426/wallets/"+ currentProfile.getProfileId(),Double.class);
		List<Statement> statements = restTemplate.getForObject("http://localhost:8426/wallets/statements/"+ currentProfile.getProfileId(),List.class);
		Ewallet wallet = new Ewallet();
		wallet.setCurrentBalance(balance);
		wallet.setStatements(statements);
		return new ModelAndView("wallet","statements",wallet);
	}

	@RequestMapping("/searchproduct")
	public ModelAndView searchProduct(@RequestParam("search") String search, Model model) {
		Product entity = restTemplate.getForObject("http://localhost:8989/products/productName/"+search,
				Product.class);
		System.out.println("products"+entity.toString());
		return new ModelAndView("ProductsByCategory", "product", entity);
	}

	@RequestMapping("/getProductsByCategory")
    public ModelAndView getCategory(@RequestParam("category") String category, Model model) {
 
        List product = restTemplate.getForObject("http://localhost:8989/products/category/" + category, List.class,
                category);
        return new ModelAndView("ProductsByCategory", "productList", product);
 
    }

	@RequestMapping("/shoppingcart")
	public ModelAndView shoppingCart() {
		System.out.println("value:" + currentProfile.getProfileId());
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		return new ModelAndView("cart", "cart", cart);
	}

	@RequestMapping("/addToCart")
	public ModelAndView addToCart(@RequestParam("quantity") int quantity,@RequestParam("productId") int productId) {
		if(currentProfile.getProfileId()==0)
		{
			return new ModelAndView("login");
		}
		boolean isItemPresent = false;
		System.out.println("productId" + productId);
		Product product = restTemplate.getForObject("http://localhost:8989/products/" + productId, Product.class);
		String productName = product.getProductName();
		double productPrice = product.getPrice();
		Items items = new Items();
		items.setProductName(productName);
		items.setPrice(productPrice);
		items.setQuantity(1);

		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		List<Items> itemsInCart = cart.getItems();

		if (itemsInCart == null)
			itemsInCart = new ArrayList<>();

		for (Items item : itemsInCart) {
			if (item.getProductName().equalsIgnoreCase(productName)) {
				item.setQuantity(1 + item.getQuantity());
				cart.setTotalPrice(cart.getTotalPrice() + (productPrice * 1));
				isItemPresent = true;
				break;
			}
		}
		if (isItemPresent == false) {
			itemsInCart.add(items);
			cart.setTotalPrice(cart.getTotalPrice() + (productPrice * 1));
		}

		cart.setItems(itemsInCart);

		restTemplate.put("http://localhost:9091/carts", cart);

		return new ModelAndView("cart", "cart", cart);
	}

	@RequestMapping("/remove")
	public ModelAndView removeProduct(@RequestParam("quantity") int quantity,
			@RequestParam("productName") String productName) {
		System.out.println("ProductName is : " + productName);
		System.out.println("Quantity is : " + quantity);
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		
		List<Items> itemsInCart = cart.getItems();
		System.out.println("Items present now" + itemsInCart);
		for (Items item : itemsInCart) {
			if (item.getProductName().equals(productName)) {
				System.out.println("Match Found !!!");
				itemsInCart.remove(item);
				System.out.println("Items present after removing" + itemsInCart);
				break;
			}
		}
		restTemplate.put("http://localhost:9091/carts", cart);
		return new ModelAndView("cart", "cart", cart);
	}

	@RequestMapping("/removeProduct")
	public ModelAndView addressForm(@RequestParam("productName") String[] productName,
			@RequestParam("quantity") int[] quantity, @RequestParam("price") double[] price) {
		
		
		
		for (int i = 0; i < productName.length; i++) {
			System.out.println("Products" + productName[i]);
		}

		List<Items> newList = new ArrayList<Items>();
		

		for (int i = 0; i < productName.length; i++) {
			Items item = new Items();
			item.setProductName(productName[i]);
			item.setPrice(price[i]);
			item.setQuantity(quantity[i]);
			System.out.println("qty" + quantity[i] + "Price" + price[i] + "Products" + productName[i]);
			newList.add(item);
		}
		
		System.out.println("NewList" + newList);
		Cart emptyCart = new Cart();
		restTemplate.postForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(), emptyCart,
				Cart.class);
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		cart.setItems(newList);
		restTemplate.put("http://localhost:9091/carts", cart);
		/*
		 * Cart emptyCart= new Cart();
		 * 
		 * restTemplate.postForObject("http://localhost:9091/carts/" +
		 * currentProfile.getProfileId(), emptyCart, Cart.class);
		 */

		/*
		 * List<Items> items = cart.getItems(); for (int i = 0; i < productName.length;
		 * i++) for (Items items2 : items) { if
		 * (!(items2.getProductName().equalsIgnoreCase(productName[i]) &&
		 * items2.getQuantity() == quantity[i])) {
		 * System.out.println("Details are not same"); } else
		 * System.out.println("Details are  same"); }
		 */
		List<Address> address = restTemplate.getForObject("http://localhost:9090/orders/"+currentProfile.getProfileId(),List.class);
		System.out.println(address.toString());
		return new ModelAndView("address","savedAddress",address);
	}

	@RequestMapping("/address")
	public ModelAndView checkout(@ModelAttribute Address address) {
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		int customerId = cart.getCartId();
		address.setCustomerId(customerId);
		staticAddress = address;
		restTemplate.postForEntity("http://localhost:9090/orders/address", address, null);
		System.out.println("address"+address.getFlatNumber());
		Address confirmAddress = address;
		return new ModelAndView("placeorder", "address", staticAddress);
	}

	@RequestMapping("/payment")
	public ModelAndView paymentMode(@RequestParam("customerId") int customerId) {

		return new ModelAndView("paymentmode", "customerId", customerId);
	}

	@RequestMapping("/codpayment")
	public ModelAndView cashOnDelivery() {
		
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		restTemplate.postForObject("http://localhost:9090/orders/cod", cart, Cart.class);
		Orders order = restTemplate.getForObject("http://localhost:9090/orders/orderId", Orders.class);
		order.setModeOfPayment("Cash on Delivery");
		Cart emptyCart = new Cart();
		restTemplate.postForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(), emptyCart,
				Cart.class);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("orders", order);
		model.put("useraddress", staticAddress);
		return new ModelAndView("ordersummary", "order", model);
	}

	@RequestMapping("/walletpayment")
	public ModelAndView proceedToPay() {
		Double wallet = restTemplate.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(),
				Double.class);
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		double totalPrice = cart.getTotalPrice();
		System.out.println("price:"+totalPrice);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cartprice",totalPrice);
		model.put("walletmoney",wallet);
		return new ModelAndView("ewallet", "wallet", model);
	}

	@RequestMapping("/paymoney")
	public ModelAndView paymoney() {
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		// System.out.println(cart);
		double totalPrice = cart.getTotalPrice();
		Double wallet = restTemplate.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(),
				Double.class);
		double walletBalance = wallet;
		if (walletBalance > totalPrice) {
			restTemplate.postForObject("http://localhost:9090/orders/onlinepay", cart, Cart.class);
			Orders order = restTemplate.getForObject("http://localhost:9090/orders/orderId", Orders.class);
			System.out.println(order);
			int orderId = order.getOrderId();
			restTemplate.put("http://localhost:8426/wallets/pay/" + currentProfile.getProfileId() + "?currentBalance="
					+ totalPrice + "&orderId=" + orderId, null);
			Double updatedBalance = restTemplate
					.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(), Double.class);
			// System.out.println("balance:"+updatedBalance);
			Cart emptyCart = new Cart();
			// List<Address>
			// address=restTemplate.getForObject("http://10.246.92.126:9090/orders/"+currentProfile.getProfileId(),
			// List.class);
			restTemplate.postForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(), emptyCart,
					Cart.class);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orders", order);
			model.put("useraddress", staticAddress);
			return new ModelAndView("ordersummary", "order", model);
		} else
			return new ModelAndView("addmoney");
	}

	@RequestMapping("/addmoney")
	public ModelAndView addMoneyToWallet(@RequestParam("money") double money) {
		restTemplate.put("http://localhost:8426/wallets/" + currentProfile.getProfileId() + "?currentBalance=" + money,
				null);
		Double updatedBalance = restTemplate
				.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(), Double.class);
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		double totalPrice = cart.getTotalPrice();
		System.out.println("price:"+totalPrice);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cartprice",totalPrice);
		model.put("walletmoney",updatedBalance);
		return new ModelAndView("ewallet", "wallet", model);
	}
	
	 
	
 ///////////////////////////////////////////////////////////   
    
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

       userService.save(userForm);
       
       UserProfile profile = new UserProfile();
		profile.setMobileNumber(userForm.getMobilenumber());
		profile.setEmailId(userForm.getEmail());
		profile.setPassword(userForm.getPassword());
		profile.setFullName(userForm.getUsername());
		UserProfile newProfile = restTemplate.postForObject("http://localhost:8080/profiles/customers", profile,
				UserProfile.class);
		restTemplate.postForEntity("http://localhost:9091/carts/" + newProfile.getProfileId(), null, Cart.class);
		restTemplate.postForEntity("http://localhost:8426/wallets/" + newProfile.getProfileId(), null, null);

		
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "login";
    }

    @RequestMapping(value = "/mlogin", method = RequestMethod.GET)
    public String login(Model model, String error, String logout,Principal principal) {
    	
    	
        if (error != null)
        	
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
        {
        
        		  
        	currentProfile.setProfileId(0);
            model.addAttribute("message", "You have been logged out successfully.");
            return "home";
        	
        }
     
        return "login";
    }
    
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome(Model model,Principal principal) {
    	
		
		  if(principal!=null) { 
		String username = principal.getName();
		  System.out.println(username);
		 profile = restTemplate.getForObject("http://localhost:8080/profiles/getbyusernames/"+
		  username, UserProfile.class);
		  if(profile == null)
			{
				  UserProfile profilegit = new UserProfile();
				  profilegit.setFullName(username);
					profile = restTemplate.postForObject("http://localhost:8080/profiles/customers", profilegit,
							UserProfile.class);
					restTemplate.postForEntity("http://localhost:9091/carts/" +profile.getProfileId(), null, Cart.class);
					restTemplate.postForEntity("http://localhost:8426/wallets/" +profile.getProfileId(), null, null);
					 currentProfile.setProfileId(profile.getProfileId());
			}
		  else
		  {
		  currentProfile.setProfileId(profile.getProfileId());
		  }
		 
		  }
		 
		  return new ModelAndView("home","profile",profile);
    			}
    
	@RequestMapping("/homelogin")
	public String homelogin() {
		return "homelogin";
	}
	
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
    
	@RequestMapping(value="/getdetails", method=RequestMethod.GET)
	public void getalldetails(Principal principal)
	{
		String username = principal.getName();
		System.out.println(username);
		UserProfile profile = restTemplate.getForObject("http://localhost:8080/profiles/getbyusernames/"+username,
				UserProfile.class);
		
		currentProfile.setProfileId(profile.getProfileId());
		System.out.println(currentProfile.getProfileId());
		System.out.println(profile.getMobileNumber() + " mobile:");
	}
	
	@RequestMapping("/searchProducts")
    public String searchProduct(@RequestParam int productId,Model model ) {
        System.out.println(productId);
        Product product = restTemplate.getForObject("http://localhost:8989/products/" + productId, Product.class);
        System.out.println("106"+ product);
        model.addAttribute(product);
    return "AllProducts";
    }
	
	

}
