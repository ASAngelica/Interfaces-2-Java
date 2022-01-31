package model.services;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService() {
	}

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) throws ParseException {

		Date date = contract.getDate();

		double basicQuota = contract.getTotalValue() / months;

		for (int i = 1; i <= months; i++) {

			// adding 30 days on each installment
			Date dueDate = addMonth(date, i);

			// adding interest and fee to the amount
			double upDatedQuota = onlinePaymentService.interest(basicQuota, i) + basicQuota;
			double fullQuota = onlinePaymentService.paymentFee(upDatedQuota) + upDatedQuota;
			
			
			contract.getInstallments().add(new Installment(dueDate, fullQuota, contract));
		}

	}
	
	public Date addMonth(Date date, Integer N) {
		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= N; i++) {
			
			cal.setTime(date);
			cal.add(Calendar.MONTH, i);
		}
		return cal.getTime();
	}
}
