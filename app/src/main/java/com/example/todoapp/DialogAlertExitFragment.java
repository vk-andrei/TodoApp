package com.example.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogAlertExitFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Activity activity = requireActivity();

        return new AlertDialog.Builder(activity)
                .setTitle("Alert!")
                .setMessage("Do you really want to Exit?")
                .setPositiveButton("Yes", (dialog, which) -> activity.finish())
                .setNegativeButton("No", null)
                .create();
    }
}