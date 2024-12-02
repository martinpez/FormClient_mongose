package mongosedb.example.mongose.view;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import mongosedb.example.mongose.R;

public class ShowViewAlert {

    public void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Acción cuando se presiona "Aceptar"
                    dialog.dismiss();
                })
                .setIcon(R.drawable.error)
                .show();
    }


    public void showAlertSuccessful(Context context, String title, String message, Runnable onSuccess, EditText... editTextsToClear) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Ejecutar acción cuando se presiona "Aceptar"
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    // Limpiar los EditText si se presiona "Cancelar"
                    if (editTextsToClear != null) {
                        for (EditText editText : editTextsToClear) {
                            editText.setText("");
                        }
                    }
                })
                .setIcon(R.drawable.successful) // Ícono de éxito
                .show();
    }


    public void showAlertQuestions(Context context, String title, String message ,Runnable onSuccess) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                })

                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();

                })
                .setIcon(R.drawable.informacion)
                .show();
    }

    public void showAlertDialogErorConexiondb(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Acción cuando se presiona "Aceptar"
                    dialog.dismiss();
                })
                .setIcon(R.drawable.conexiondb)
                .show();
    }



}
