
/***************************************************/
/* Adapted from HW6 solution by Renato Mancuso
                    */
/*                                                 */
/* Description: This class implements a descriptor */
/*   for a unit of work that needs to be processed */
/*   by the generic worker thread. The class also  */
/*   incorporates a field to save the result of    */
/*   the performed computation.                    */
/*                                                 */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/***************************************************/

public class WorkUnit {

    String hash;
    String result;
    boolean isCompoundHint;
    int start;
    int end;
    List<WorkUnitGroup> groups = new ArrayList<>();
    boolean isCanceled = false;
    Set<String> hashesToCheck;

    /* Simple constructor to set the input hash */
    public WorkUnit (String hash) {
      this.hash = hash;
      this.result = null;
      this.isCompoundHint = false;
    }

    public WorkUnit(int start, int end, Set<String> hashes){
        this.hash = null;
        this.result = null;
        this.isCompoundHint = true;
        this.hashesToCheck = hashes;
        this.start = start;
        this.end = end;
    }

    public String getHash() {
	return hash;
    }
    public void setHash(String toSet) {
        this.hash = toSet;
    }

    public boolean isHint(){
        return this.isCompoundHint;
    }
    public boolean isCanceled(){
        return this.isCanceled;
    }
    public void cancel(){
        this.isCanceled = true;
    }
    public void addGroup(WorkUnitGroup group){
        this.groups.add(group);
    }
    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    /* These can be handy to generalize the boundaries of hash
     * cracking */
    public int getLowerBound() {
	return 0;
    }

    public int getUpperBound() {
	return Integer.MAX_VALUE;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public String getResult() {
	return result;
    }

    public boolean isCompoundHint(){
        return this.isCompoundHint;
    }

    /* Render this WorkUnit when printed */
    @Override
    public String toString() {
      if (this.result != null)
          return this.result;
      else if (this.hash != null) {
          return this.hash;
      } else {
          return this.start + " " + this.end;
      }

    }
    
}
