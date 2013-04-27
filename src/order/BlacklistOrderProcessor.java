
package order;

import eventbroker.EventBroker;
import java.util.HashSet;
import java.util.Set;


public class BlacklistOrderProcessor extends OrderProcessor {

    private Set<String> blacklist;
    
    private BlacklistOrderProcessor(){
        //super(); //op dit moment gaat de constructor this meegeven aan create, maar de constructor is nog nie afgelopen
        
        // some work to get to the blacklist data
        
    }
    
    public static BlacklistOrderProcessor create(){
        BlacklistOrderProcessor bop = new BlacklistOrderProcessor();
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {}
       
        // add the blacklisted customers
        Set<String> blacklist = new HashSet<String>();
        blacklist.add("Jan");
        bop.setBlacklist(blacklist);
        EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, bop);
        return bop;
    }
    
    @Override
    protected void processOrder(OrderEvent order){
        // ignore blacklisted customers
        if(blacklist.contains(order.getCustomer())){
            System.out.println("Order of customer "+order.getCustomer()+" discarded");
            return;
        }
        
        super.processOrder(order);
    }

    public Set<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Set<String> blacklist) {
        this.blacklist = blacklist;
    }
    
}
