package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * An event model that was built for automatic serialization from json to object.
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */
public class Event {

    @Expose
    @SerializedName("id")
    private String mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("fullname")
    private String mFullname;
    @Expose
    @SerializedName("startTime")
    private Date mStartTime;
    @Expose
    @SerializedName("endTime")
    private Date mEndTime;
    @Expose
    @SerializedName("color")
    private String mColor;
    @Expose
    @SerializedName("type")
    private String mType;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getFullname() {
        return mFullname;
    }

    public void setFullname(String mFullname) {
        this.mFullname = mFullname;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date startTime) {
        this.mStartTime = startTime;
    }

    public Date getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Date endTime) {
        this.mEndTime = endTime;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    @SuppressLint("SimpleDateFormat")
    public WeekViewEvent toWeekViewEvent(Calendar calendar){

        // Initialize start and end time.
        Calendar startTime = (Calendar) calendar.clone();
        startTime.setTimeInMillis(getStartTime().getTime());
        startTime.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        startTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        Calendar endTime = (Calendar) startTime.clone();
        endTime.setTimeInMillis(getEndTime().getTime());
        endTime.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        endTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        // Create an week view event.
        WeekViewEvent weekViewEvent = new WeekViewEvent();
        weekViewEvent.setId(Long.parseLong(getId()));
        weekViewEvent.setName(getName());
        weekViewEvent.setStartTime(startTime);
        weekViewEvent.setEndTime(endTime);
        weekViewEvent.setColor(Color.parseColor(getColor()));
        weekViewEvent.setType(getType());
        weekViewEvent.setFullname(getFullname());

        return weekViewEvent;
    }
}
