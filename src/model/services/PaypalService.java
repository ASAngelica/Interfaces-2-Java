package model.services;

public class PaypalService implements OnlinePaymentService {
	
	public static final double simpleInterest = 0.01; //1%
	public static final double paymentFee = 0.02; //2%
	
	@Override
	public Double paymentFee(Double amount) {
		return amount * paymentFee ;
	}
	
	@Override
	public Double interest(Double amount, Integer months) {
		return  amount * simpleInterest * months;
	}
}
