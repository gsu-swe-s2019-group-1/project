package pw.wp6.avocado_toast.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import pw.wp6.avocado_toast.model.LedgerEntry;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserTransactions
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-02T16:08:55.885-05:00[America/New_York]")
public class UserTransactions   {
  @JsonProperty("balance")
  private BigDecimal balance = null;

  @JsonProperty("transactions")
  @Valid
  private List<LedgerEntry> transactions = null;

  public UserTransactions balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  **/
  @ApiModelProperty(value = "")

  @Valid
  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public UserTransactions transactions(List<LedgerEntry> transactions) {
    this.transactions = transactions;
    return this;
  }

  public UserTransactions addTransactionsItem(LedgerEntry transactionsItem) {
    if (this.transactions == null) {
      this.transactions = new ArrayList<LedgerEntry>();
    }
    this.transactions.add(transactionsItem);
    return this;
  }

  /**
   * List of transactions in order from most recent to least recent
   * @return transactions
  **/
  @ApiModelProperty(value = "List of transactions in order from most recent to least recent")
  @Valid
  public List<LedgerEntry> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<LedgerEntry> transactions) {
    this.transactions = transactions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserTransactions userTransactions = (UserTransactions) o;
    return Objects.equals(this.balance, userTransactions.balance) &&
        Objects.equals(this.transactions, userTransactions.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(balance, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserTransactions {\n");
    
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
