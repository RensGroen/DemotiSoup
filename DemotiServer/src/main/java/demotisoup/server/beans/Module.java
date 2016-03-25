package demotisoup.server.beans;

/**
 * User: rgroenveld
 * Date: 21-3-16
 * Time: 19:22
 */
public class Module {

  int id;
  private String title;
  private String description;

  public Module(int id, String title, String description){
    super();
    this.id = id;
    this.title = title;
    this.description = description;
  }
  public int getId(){
    return id;
  }

  public void setId(int id){
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
