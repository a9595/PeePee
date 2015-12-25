package tieorange.edu.googlemapstest.adapters;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;

import tieorange.edu.googlemapstest.R;

/**
 * Created by tieorange on 25/12/15.
 */
public class CustomCardProvider extends CardProvider<CustomCardProvider> {
    private String mName;
    private String mDescription;

    public CustomCardProvider setName(String text) {
        this.mName = text;
        notifyDataSetChanged();
        return this;
    }

    public CustomCardProvider setDescription(String description) {
        this.mDescription = description;
        notifyDataSetChanged();
        return this;
    }

    @Override
    public void render(@Nullable View view, @Nullable Card card) {
        super.render(view, card);

        TextView mUiTextViewName = (TextView) view.findViewById(R.id.listview_item_firstLine);
        TextView mUiTextViewDescription = (TextView) view.findViewById(R.id.listview_item_firstLine);

        mUiTextViewName.setText(mName);
        mUiTextViewDescription.setText(mDescription);

    }

    @Override
    public int getLayout() {
        return R.layout.listview_item;
    }
}
