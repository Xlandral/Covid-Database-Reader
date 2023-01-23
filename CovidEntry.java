public class CovidEntry implements Comparable {
    private String state;
    private int month;
    private int day;
    private int dailyDeaths;
    private int dailyInfections;
    private int totalDeaths;
    private int totalInfections;

    //Constructor
    public CovidEntry(String st, int m, int d, int di, int dd, int ti, int td) {
        state = st;
        month = m;
        day = d;
        dailyInfections = di;
        dailyDeaths = dd;
        totalDeaths = td;
        totalInfections = ti;
    }

    //Returns month
    public int getMonth() {
        return month;
    }

    //Returns day
    public int getDay() {
        return day;
    }

    //Returns state
    public String getState() {
        return state;
    }

    //Returns daily infections
    public int getDailyInfections() {
        return dailyInfections;
    }

    //Returns daily deaths
    public int getDailyDeaths() {
        return dailyDeaths;
    }

    //Returns total infections
    public int getTotalInfections() {
        return totalInfections;
    }

    //returns total deaths
    public int getTotalDeaths() {
        return totalDeaths;
    }

    //puts the information to a string
    public String toString() {
        return state + " " + month 
        + "/" + day + " " + dailyInfections
        + " infections, " + dailyDeaths
        + " deaths";
    }

    public int compareTo(Object other){
        CovidEntry c = (CovidEntry) other;
        return c.dailyDeaths - dailyDeaths;
        }
}
