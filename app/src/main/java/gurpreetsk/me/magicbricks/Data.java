package gurpreetsk.me.magicbricks;

/**
 * Created by daman on 23/7/16.
 */

public class Data {

    private double lon;
    private double lat;
    private String name;
    private int noOfRooms;
    private int rent;
    private boolean isSharing;
    private String email;
    private String locality;
    //private Boolean isOwner;
    // private String image;

    public Data() {
               /*Blank default constructor essential for Firebase*/
    }

    public Data(String name, double lat, double lon, String email, int noOfRooms, Boolean isSharing, int rent, String locality) {
        this.lon = lon;
        this.lat = lat;
        this.name = name;
        this.noOfRooms = noOfRooms;
        this.rent = rent;
        this.isSharing = isSharing;
        this.email = email;
        this.locality = locality;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public boolean isSharing() {
        return isSharing;
    }

    public void setSharing(boolean sharing) {
        isSharing = sharing;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

}