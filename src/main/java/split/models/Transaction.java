package split.models;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

  //    name of paidByUser
  @NonNull private String paidFrom;

  //    names of paidToUsers
  @NonNull private List<String> paidTo;

  @NonNull private int totalAmount;

  private float amountPerHead;

  @NonNull private String cause;

  private Boolean selfIncluded;

  private Instant createTs;

  public float getAmountPerHead() {

    int size = paidTo.size();

    if (Boolean.TRUE.equals(this.selfIncluded) && !this.getPaidTo().contains(this.paidFrom))
      this.paidTo.add(this.paidFrom);
    else if (!Boolean.TRUE.equals(this.selfIncluded) && this.getPaidTo().contains(this.paidFrom))
      this.selfIncluded = Boolean.TRUE;

    return (float) totalAmount / size;
  }
}
