import java.util.ArrayList;
import java.util.List;

public class WorkUnitGroup {

  private int relatedBy;
  List<WorkUnit> group = new ArrayList<>();

  public WorkUnitGroup(int by){
    this.relatedBy = by;
  }

  public void addUnit(WorkUnit x){
    this.group.add(x);
  }

  public void cancelAll(){
    //System.out.println("Canceling group " + relatedBy);
    for (WorkUnit g : group){
      //System.out.println("Canceling workunit " + g);
      g.cancel();
    }
  }

}
