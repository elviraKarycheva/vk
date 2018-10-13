package com.example.karyc.vkontaktikum.ui.contacts;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.example.karyc.vkontaktikum.core.Friend;
import com.example.karyc.vkontaktikum.core.RetrofitProvider;
import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.GetFriendsResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.example.karyc.vkontaktikum.ui.LoginActivity.SAVED_ACCESS_TOKEN;

public class ContactsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contact>> contactsLiveData = new MutableLiveData<>();

    public ContactsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Contact>> getContactsLiveData() {
        return contactsLiveData;
    }

    public void loadData() {
        Single<List<ContactModel>> localContactsSingle = getLocalContactsSingle();
        Single<List<Friend>> serverContactsSingle = getServerContactsSingle();

        localContactsSingle
                .zipWith(serverContactsSingle, new BiFunction<List<ContactModel>, List<Friend>, List<Contact>>() {
                    @Override
                    public List<Contact> apply(List<ContactModel> contactModels, List<Friend> friends) throws Exception {
                        ArrayList<Contact> common = new ArrayList<>();//new class

                        for (Friend currentItem : friends) {
                            for (ContactModel currentContact : contactModels) {
                                for (String currentMobileContact : currentContact.getContactNumber()) {
                                    if (currentMobileContact.equals(currentItem.getMobilePhone())) {
                                        String name = currentItem.getFirstName() + " " + currentItem.getLastName();
                                        Contact contact = new Contact(name, currentItem.getMobilePhone(), currentContact.getContactPhoto());
                                        common.add(contact);
                                    }
                                }
                            }
                        }

                        return common;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Contact>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        contactsLiveData.setValue(contacts);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    private Single<List<ContactModel>> getLocalContactsSingle() {
        return Single
                .create(new SingleOnSubscribe<List<ContactModel>>() {
                    @Override
                    public void subscribe(SingleEmitter<List<ContactModel>> e) throws Exception {
                        ArrayList<ContactModel> result = readContacts();
                        e.onSuccess(result);
                    }
                });
    }

    private ArrayList<ContactModel> readContacts() {
        ArrayList<ContactModel> contactList = new ArrayList<>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactsCursor = getApplication().getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        if (contactsCursor.moveToFirst()) {
            do {
                long contctId = contactsCursor.getLong(contactsCursor
                        .getColumnIndex("_ID"));
                Uri dataUri = ContactsContract.Data.CONTENT_URI;
                Cursor dataCursor = getApplication().getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
                        null, null);

                String displayName = "";
                String nickName = "";
                ArrayList<String> mobilePhone = new ArrayList<>();
                String photoPath = null; // Photo path
                byte[] photoByte = null;// Byte to get photo since it will come
                String homeEmail = "";
                String workEmail = "";
                String companyName = "";
                String title = "";
                String contactEmailAddresses = "";
                String contactOtherDetails = "";

                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    do {
                        if (dataCursor
                                .getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
                            nickName = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1")); // Get Nick Name
                            contactOtherDetails += "NickName : " + nickName
                                    + "n";// Add the nick name to string
                        }

                        if (dataCursor
                                .getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            String newNumber = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1"));
                            mobilePhone.add(newNumber);
                        }

                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactEmailAddresses += "Home Email : "
                                            + homeEmail + "n";
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    workEmail = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactEmailAddresses += "Work Email : "
                                            + workEmail + "n";
                                    break;
                            }
                        }

                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                            companyName = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1"));
                            contactOtherDetails += "Company Name : "
                                    + companyName + "n";
                            title = dataCursor.getString(dataCursor
                                    .getColumnIndex("data4"));
                            contactOtherDetails += "Title : " + title + "n";
                        }

                        if (dataCursor
                                .getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = dataCursor.getBlob(dataCursor
                                    .getColumnIndex("data15"));

                            if (photoByte != null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(
                                        photoByte, 0, photoByte.length);
                                File cacheDirectory = getApplication()
                                        .getCacheDir();
                                File tmp = new File(cacheDirectory.getPath()
                                        + "/_androhub" + contctId + ".png");
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(
                                            tmp);
                                    bitmap.compress(Bitmap.CompressFormat.PNG,
                                            100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                photoPath = tmp.getPath();
                            }
                        }
                    } while (dataCursor.moveToNext());

                    ContactModel newContact = new ContactModel(Long.toString(contctId),
                            displayName, mobilePhone, contactEmailAddresses,
                            photoPath, contactOtherDetails);

                    contactList.add(newContact);
                }
            } while (contactsCursor.moveToNext());
        }
        return contactList;
    }

    private Single<List<Friend>> getServerContactsSingle() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);
        FriendsApi friendsApi = RetrofitProvider.getFriendsApi();

        return friendsApi
                .getAllFriends(accessToken, "5.80", "contacts")
                .flatMap(new Function<CommonResponse<GetFriendsResponse>, SingleSource<? extends List<Friend>>>() {
                    @Override
                    public SingleSource<? extends List<Friend>> apply(final CommonResponse<GetFriendsResponse> getFriendsResponseCommonResponse) throws Exception {
                        return Single.create(new SingleOnSubscribe<List<Friend>>() {
                            @Override
                            public void subscribe(SingleEmitter<List<Friend>> e) throws Exception {
                                e.onSuccess(getFriendsResponseCommonResponse.response.items);
                            }
                        });
                    }
                });
    }
}
