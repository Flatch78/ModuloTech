package com.app.krier.modulotechtest.manager.json;

import com.app.krier.modulotechtest.models.ContactModel;
import com.app.krier.modulotechtest.models.LocationModel;
import com.app.krier.modulotechtest.models.MeModel;
import com.app.krier.modulotechtest.models.NameModel;
import com.app.krier.modulotechtest.models.PictureModel;
import com.app.krier.modulotechtest.models.RootModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuillaumeK on 16/03/2017.
 */

public class JsonRequest {
    public RootModel makeObjectRootModel(String jsonResponse) {
        RootModel rootModel = new RootModel();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            rootModel.me = makeMeModel(root.getJSONObject("me"));
            rootModel.contacts = makeListContactModel(root.getJSONArray("contacts"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootModel;
    }

    private List<ContactModel> makeListContactModel(JSONArray contacts) throws JSONException {
        List<ContactModel> contactModelList = new ArrayList<ContactModel>();
        for (int i = 0; i < contacts.length(); ++i) {
            JSONObject contact = contacts.getJSONObject(i);
            ContactModel contactModel = new ContactModel();

            contactModel.name = makeNameModel(contact.getJSONObject("name"));
            contactModel.location = makeLocationModel(contact.getJSONObject("location"));
            contactModel.picture = makePictureModel(contact.getJSONObject("picture"));

            contactModel.gender = contact.getString("gender");
            contactModel.email = contact.getString("email");
            contactModel.username = contact.getString("username");
            contactModel.password = contact.getString("password");
            contactModel.phone = contact.getString("phone");
            contactModel.cell = contact.getString("cell");
            contactModel.SSN = contact.getString("SSN");

            contactModelList.add(contactModel);
        }
        return contactModelList;
    }


    private MeModel makeMeModel(JSONObject me) throws JSONException {
        MeModel meModel = new MeModel();

        meModel.name = makeNameModel(me.getJSONObject("name"));
        meModel.location = makeLocationModel(me.getJSONObject("location"));
        meModel.picture = makePictureModel(me.getJSONObject("picture"));

        meModel.gender = me.getString("gender");
        meModel.email = me.getString("email");
        meModel.username = me.getString("username");
        meModel.password = me.getString("password");
        meModel.phone = me.getString("phone");
        meModel.cell = me.getString("cell");
        meModel.SSN = me.getString("SSN");
        return meModel;
    }

    private NameModel makeNameModel(JSONObject name) throws JSONException {
        NameModel nameModel = new NameModel();
        nameModel.title = name.getString("title");
        nameModel.first = name.getString("first");
        nameModel.last = name.getString("last");
        return nameModel;
    }

    private LocationModel makeLocationModel(JSONObject location) throws JSONException {
        LocationModel locationModel = new LocationModel();
        locationModel.street = location.getString("street");
        locationModel.city = location.getString("city");
        locationModel.state = location.getString("state");
        locationModel.zip = location.getString("zip");
        return locationModel;
    }

    private PictureModel makePictureModel(JSONObject picture) throws JSONException {
        PictureModel pictureModel = new PictureModel();
        pictureModel.large = picture.getString("large");
        pictureModel.medium = picture.getString("medium");
        pictureModel.thumbnail = picture.getString("thumbnail");

        /* FIXME: Bad URL HTTP -> HTTPS */
        pictureModel.large = pictureModel.large.replaceAll("http://", " https://");
        pictureModel.medium = pictureModel.medium.replaceAll("http://", " https://");
        pictureModel.thumbnail = pictureModel.thumbnail.replaceAll("http://", " https://");

        return pictureModel;
    }

}
