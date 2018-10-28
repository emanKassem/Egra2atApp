package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.utils.PrefUtils;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MinistriesFragment extends Fragment {

    @BindView(R.id.ministriesRecyclerView)
    RecyclerView ministriesRecyclerView;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.logout)
    ImageView logout;
    Context context = App.getContext();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ministry_fragment, container, false);
        ButterKnife.bind(this, view);
        toolbar_title.setText("الوزارات");
        Map<String, Integer> ministries = new HashMap<>();
        ministries.put("وزارة النقل", R.mipmap.naql);
        ministries.put("وزارة الإسكان", R.mipmap.eskan);
        ministries.put("وزارة الإعلام", R.mipmap.media);
        ministries.put("وزارة الإتصالات وتقنية المعلومات", R.mipmap.etsalat);
        ministries.put("وزارة الإقتصاد والتخطيط", R.mipmap.ektsad);
        ministries.put("وزارة البيئة والمياه والزراعة", R.mipmap.be2a);
        ministries.put("وزارة التجارة والاستثمار", R.mipmap.tegara);
        ministries.put("وزارة التعليم", R.mipmap.ta3lem);
        ministries.put("وزارة الثقافة", R.mipmap.culture);
        ministries.put("وزارة الحج والعمرة", R.mipmap.a7ag);
        ministries.put("وزارة الحرس الوطنى", R.mipmap.a7ars);
        ministries.put("وزارة الخارجية", R.mipmap.a5argya);
        ministries.put("وزارة الخدمة المدنية", R.mipmap.a5edma);
        ministries.put("وزارة الداخلية", R.mipmap.da5lya);
        ministries.put("وزارة الدفاع", R.mipmap.deffaa);
        ministries.put("وزارة الشئون الإسلامية والدعوة والارشاء", R.mipmap.islamic);
        ministries.put("وزارة الشئون البلدية والقروية", R.mipmap.baladya);
        ministries.put("وزارة الصحة", R.mipmap.se77a);
        ministries.put("وزارة الطاقة والصناعة والثروة المعدنية", R.mipmap.taqa);
        ministries.put("وزارة العدل", R.mipmap.a3adl);
        ministries.put("وزارة العمل والتنمية الاجتماعية", R.mipmap.a3ml);
        ministries.put("وزارة المالية", R.mipmap.malya);
        String tabletSize = getResources().getString(R.string.phoneType);
        RecyclerView.LayoutManager manager;
        if (tabletSize.equals("normal")){
            manager = new GridLayoutManager(context, 2);
        }else if (tabletSize.equals("land")){
            manager = new GridLayoutManager(context, 3);
        }else {
            manager = new GridLayoutManager(context, 4);
        }
        ministriesRecyclerView.setLayoutManager(manager);
        MinistriesAdapter adapter = new MinistriesAdapter(ministries);
        ministriesRecyclerView.setAdapter(adapter);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                PrefUtils.storeKeys(context, getString(R.string.login_key), null);
                startActivity(intent);
                Toast.makeText(context, "تم تسجيل الخروج", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
        return view;
    }
}
