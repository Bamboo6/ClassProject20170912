package cn.edu.gdmec.android.classproject20170912.m2theftguard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.classproject20170912.R;
import cn.edu.gdmec.android.classproject20170912.m2theftguard.BaseSetupActivity;
import cn.edu.gdmec.android.classproject20170912.m2theftguard.entity.ContactInfo;

/**
 * Created by lt on 2017/10/19.
 */

public class ContactAdapter extends BaseAdapter{

    private List<ContactInfo> contactInfos;
    private Context context;
    public ContactAdapter(List<ContactInfo> contactInfos,Context context){
        super();
        this.contactInfos = contactInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contactInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return contactInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = View.inflate(context, R.layout.item_list_contact_select,null);
            holder = new ViewHolder();
            holder.mNameTV = (TextView) view.findViewById(R.id.tv_name);
            holder.mPhoneTV = (TextView) view.findViewById(R.id.tv_phone);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mNameTV.setText(contactInfos.get(i).name);
        holder.mPhoneTV.setText(contactInfos.get(i).phone);
        return view;
    }
    static class ViewHolder{
        TextView mNameTV;
        TextView mPhoneTV;
    }
}
