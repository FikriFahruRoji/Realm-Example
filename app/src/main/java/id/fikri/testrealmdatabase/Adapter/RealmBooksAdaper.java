package id.fikri.testrealmdatabase.Adapter;

import android.content.Context;

import id.fikri.testrealmdatabase.Model.Book;
import io.realm.RealmResults;

/**
 * Created by fikri on 22/12/16.
 */

public class RealmBooksAdaper extends RealmModelAdapter<Book> {

    public RealmBooksAdaper(Context context, RealmResults<Book> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}