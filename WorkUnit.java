
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
/***************************************************/

public class WorkUnit {

    String hash;
    String result;
    boolean isCompoundHint = false;

    /* Simple constructor to set the input hash */
    public WorkUnit (String hash) {
      this.hash = hash;
      this.result = null;
    }

    public String getHash() {
	return hash;
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
      else
          return this.hash;
        }
    
}
