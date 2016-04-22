package se.rickylagerkvist.owni.ui.ActivityCardItemActivity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;

import com.firebase.client.Firebase;

import se.rickylagerkvist.owni.R;
import se.rickylagerkvist.owni.model.ActivityCardItem;
import se.rickylagerkvist.owni.utils.Constants;

/**
 * Created by Ricky on 2016-04-22.
 */
public class AddActivitiesCardItemDialog extends DialogFragment {

    EditText mEditTextDescription, mEditTextAmount, mEditTextValue, mEditTextName;
    RadioButton mRadioButtonIowe, mRadioButtonSomeoneOwesMe;

    public static AddActivitiesCardItemDialog newInstance(String peopleCardId) {
        AddActivitiesCardItemDialog addListDialogFragment
                = new AddActivitiesCardItemDialog();
        Bundle bundle = new Bundle();
        bundle.putString("id", peopleCardId);
        addListDialogFragment.setArguments(bundle);
        return addListDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Open the keyboard automatically when the dialog fragment is opened
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.add_activities_card_item_dialog, null);

        Bundle bundle = this.getArguments();
        final String peopleCardId = bundle.getString("id");

        // Views
        mEditTextDescription = (EditText) rootView.findViewById(R.id.edit_description);
        mEditTextAmount = (EditText) rootView.findViewById(R.id.edit_amount);
        mEditTextValue = (EditText) rootView.findViewById(R.id.edit_value);
        mRadioButtonIowe = (RadioButton) rootView.findViewById(R.id.i_owe_radiobutton);
        mRadioButtonSomeoneOwesMe = (RadioButton) rootView.findViewById(R.id.someone_owes_me_radiobutton);
        mEditTextName = (EditText) rootView.findViewById(R.id.edit_name);

        // set text with correct name
        mRadioButtonIowe.setText(getActivity().getString(R.string.i_owe_person));
        mRadioButtonSomeoneOwesMe.setText(getActivity().getString(R.string.someone_owe_me));

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                .setTitle(getActivity().getString(R.string.new_activitiescard_item))
                .setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog
                        AddActivitiesCardItemDialog.this.getDialog().cancel();
                    }
                })
                // Add action buttons
                .setPositiveButton(getActivity().getString(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addPeopleCardItemToList(peopleCardId);
                    }
                });

        return builder.create();
    }

    private void addPeopleCardItemToList(String peopleCardId) {
        String userEnteredDescription = mEditTextDescription.getText().toString().trim();
        int userEnteredAmount = Integer.valueOf(mEditTextAmount.getText().toString().trim());
        String userEnteredValue = mEditTextValue.getText().toString().trim();
        String userEnteredName = mEditTextName.getText().toString().trim();

        String userUid = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("USERUID", "defaultStringIfNothingFound");

        if (mRadioButtonIowe.isChecked() && !userEnteredDescription.equals("") && userEnteredAmount >= 0 && !userEnteredValue.equals("")) {

            Firebase listsRef = new Firebase(Constants.FIREBASE_URL_ACTIVITIES_ITEMS + "/" + userUid).child(peopleCardId).child("iowe");
            Firebase newListRef = listsRef.push();

            //PeopleCard
            ActivityCardItem activityCardItem = new ActivityCardItem(userEnteredDescription, userEnteredAmount, userEnteredValue, userEnteredName, true);

            //Add to the list
            newListRef.setValue(activityCardItem);

            // Close dialog
            AddActivitiesCardItemDialog.this.getDialog().cancel();

        } else if (mRadioButtonSomeoneOwesMe.isChecked() && !userEnteredDescription.equals("") && userEnteredAmount >= 0 && !userEnteredValue.equals("")) {

            Firebase listsRef = new Firebase(Constants.FIREBASE_URL_ACTIVITIES_ITEMS + "/" + userUid).child(peopleCardId).child("xowes");
            Firebase newListRef = listsRef.push();

            //PeopleCard
            ActivityCardItem activityCardItem = new ActivityCardItem(userEnteredDescription, userEnteredAmount, userEnteredValue, userEnteredName, false);

            //Add to the list
            newListRef.setValue(activityCardItem);

            // Close dialog
            AddActivitiesCardItemDialog.this.getDialog().cancel();

        } else {
            AddActivitiesCardItemDialog.this.getDialog().cancel();
        }

    }


}
