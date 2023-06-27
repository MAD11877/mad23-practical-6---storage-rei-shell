package sg.edu.np.mad.practical6;

public class User {
    public String name;
    public String description;
    public int id;
    public boolean followed;

    public User(){}

    public User(String Name, String Description, int Id, boolean Followed){
        this.name = Name;
        this.description = Description;
        this.id = Id;
        this.followed = Followed;
    }

    public String getMyName() {
        return name;
    }

    public void setMyName(String myName) {
        this.name = myName;
    }

    public String getMyDesc() {
        return description;
    }

    public void setMyDesc(String myDesc) {
        this.description = myDesc;
    }

    public void setMyFollow(Boolean myFollow){
        this.followed = myFollow;
    }

}