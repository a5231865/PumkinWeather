package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import material.pumkinweather.hence.com.pumkinweather.R;

/**
 * Created by Hence on 2016/8/2.
 */
public class suggesstionAdapter extends RecyclerView.Adapter<suggesstionAdapter.suggViewHolder>  {
    private LayoutInflater layoutInflater;
    private Context context;
    ArrayList<String> suggestiontype=new ArrayList<>();
    ArrayList<String> suggestionbrf=new ArrayList<>();
    ArrayList<String> suggestiontxt=new ArrayList<>();
    public static final String LOG_TAG = "Log";


    @Override
    public suggViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.suggestionlayout, parent,false);
        suggViewHolder viewholder= new suggViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(suggViewHolder holder, int position) {

        holder.suggtype.setText(suggestiontype.get(position));
        holder.suggbrf.setText(suggestionbrf.get(position));
        holder.suggtxt.setText(suggestiontxt.get(position));
    }

    @Override
    public int getItemCount() {
        int cnt=0;
        cnt=Math.min(suggestionbrf.size(),suggestiontxt.size());
        cnt=Math.min(cnt,suggestiontype.size());
        Log.d(LOG_TAG,"Sugg adapter size"+cnt);
        return cnt;
    }

    public suggesstionAdapter(Context context,ArrayList<String> suggestiontype,ArrayList<String> suggestionbrf, ArrayList<String> suggestiontxt){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.suggestiontxt=suggestiontxt;
        this.suggestionbrf=suggestionbrf;
        this.suggestiontype=suggestiontype;
    }

    public class suggViewHolder extends RecyclerView.ViewHolder{
        private TextView suggtype;
        private TextView suggbrf;
        private TextView suggtxt;

        public suggViewHolder(View itemView) {
            super(itemView);
            suggtype= (TextView) itemView.findViewById(R.id.suggtype);
            suggbrf= (TextView) itemView.findViewById(R.id.suggbrf);
            suggtxt= (TextView) itemView.findViewById(R.id.suggtext);
        }
    }
}
