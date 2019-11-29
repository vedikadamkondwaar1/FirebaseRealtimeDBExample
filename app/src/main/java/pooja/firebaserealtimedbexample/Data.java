package pooja.firebaserealtimedbexample;

public class Data {
    String title, note, date, id, desc;

    public Data() {

    }

    public Data(String title, String note, String date, String id, String desc) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.id = id;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
