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
 * DailyTransactions
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-04T01:09:12.686-05:00[America/New_York]")
public class DailyTransactions   {
  @JsonProperty("cashFlow")
  private BigDecimal cashFlow = null;

  @JsonProperty("transactions")
  @Valid
  private List<LedgerEntry> transactions = null;

  public DailyTransactions cashFlow(BigDecimal cashFlow) {
    this.cashFlow = cashFlow;
    return this;
  }

  /**
   * Sum of the value of all the transactions on the given date
   * @return cashFlow
  **/
  @ApiModelProperty(value = "Sum of the value of all the transactions on the given date")

  @Valid
  public BigDecimal getCashFlow() {
    return cashFlow;
  }

  public void setCashFlow(BigDecimal cashFlow) {
    this.cashFlow = cashFlow;
  }

  public DailyTransactions transactions(List<LedgerEntry> transactions) {
    this.transactions = transactions;
    return this;
  }

  public DailyTransactions addTransactionsItem(LedgerEntry transactionsItem) {
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
    DailyTransactions dailyTransactions = (DailyTransactions) o;
    return Objects.equals(this.cashFlow, dailyTransactions.cashFlow) &&
        Objects.equals(this.transactions, dailyTransactions.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cashFlow, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DailyTransactions {\n");
    
    sb.append("    cashFlow: ").append(toIndentedString(cashFlow)).append("\n");
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
