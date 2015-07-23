package bb.api;

import java.util.Comparator;

public class BidAmountComparator implements Comparator<Bid> {

	@Override
	public int compare(Bid o1, Bid o2) {
		return o1.getBidAmount()-o2.getBidAmount();
	}

}
