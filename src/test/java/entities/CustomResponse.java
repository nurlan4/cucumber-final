package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

    private int category_id;
    private String created;

    private int number_of_invoices;

    private int seller_id;

    private String seller_name;

    private List<CustomResponse> responses;

    private String email;

    private int id;

    private int balance;

    private String bank_account_name;

    private String responseBody;

    private String company_name;

    private String phone_number;

    private int status;
}

