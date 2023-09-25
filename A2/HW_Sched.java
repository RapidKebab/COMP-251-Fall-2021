import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
    int deadline;
    int completiontime;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline, int completiontime) {
		this.number = number;
		this.weight = weight;
        this.deadline = deadline;
        this.completiontime = completiontime;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// TODO Implement this
		if (a1.weight/a1.completiontime > a2.weight/a2.completiontime) {
			return -1;
		}
		if (a1.weight/a1.completiontime < a2.weight/a2.completiontime) {
			return 1;
		}
		if (a1.deadline < a2.deadline) {
			return -1;
		}
		if (a1.deadline > a2.deadline) {
			return 1;
		}
		return 0;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
    int lastDeadline = 0;
    double grade = 0.0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int[] completiontimes, int size) throws Exception {
        if(size==0){
            throw new Exception("There is no assignment.");
        }
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i], completiontimes[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public ArrayList<Integer> SelectAssignments() {
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
		for(Assignment a: Assignments){System.out.print(a.number + " ");}
		System.out.println("");
        //TODO Implement this

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		ArrayList<Integer> homeworkPlan = new ArrayList<>();
		for (int i=0; i < lastDeadline; ++i) {
			homeworkPlan.add(-1);
		}

		int timepointer = 0;
		for(Assignment a: Assignments){
			if(timepointer<a.deadline) {
				for (int j = 0; j < a.completiontime; j++) {
					if(timepointer<a.deadline)
						homeworkPlan.set(timepointer,a.number);
					else
						homeworkPlan.add(timepointer,a.number);
					timepointer++;
				}
				if(timepointer>a.deadline)
					grade+=a.weight-((a.weight*0.1)*(timepointer-a.deadline));
				else
					grade += a.weight;
			}
		}
		System.out.println(grade);
		return homeworkPlan;
	}
	public static void main(String[] args) throws Exception {
		int[] weights = {10,30};
		int[] deadline = {4,4};
		int[] compTime = {3,3};
		HW_Sched test = new HW_Sched(weights,deadline,compTime,2);
		System.out.println(test.SelectAssignments());

	}
}
	



