package jekirdek.com.t3mobil.model;

/**
 * Created by cem on 6.08.2017.
 */
public class AttendeceListModel {

    private String name;
    private boolean checked;

    public AttendeceListModel(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
