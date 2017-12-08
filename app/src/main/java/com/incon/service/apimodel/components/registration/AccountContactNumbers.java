package com.incon.service.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountContactNumbers {

@SerializedName("account_id")
@Expose
private Integer accountId;
@SerializedName("contact_number_id")
@Expose
private Integer contactNumberId;

public Integer getAccountId() {
return accountId;
}

public void setAccountId(Integer accountId) {
this.accountId = accountId;
}

public Integer getContactNumberId() {
return contactNumberId;
}

public void setContactNumberId(Integer contactNumberId) {
this.contactNumberId = contactNumberId;
}

}