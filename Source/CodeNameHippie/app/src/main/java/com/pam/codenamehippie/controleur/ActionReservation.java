package com.pam.codenamehippie.controleur;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.DepotManager;

/**
 * Classe servant à encapsuler les fonctions de button d'item de liste view de réservation.
 */
public final class ActionReservation implements OnClickListener,
                                                DialogInterface.OnClickListener {

    /**
     * Button supprimer
     */
    private final View supprimerButton;
    /**
     * Button collecter
     */
    private final View collecterButton;

    /**
     * Context pour accéder au strings.
     */
    private final Context context;

    /**
     * Objet à modifier.
     */
    private final AlimentaireModele modele;

    /**
     * Dépot pour gérer les requête.
     */
    private final AlimentaireModeleDepot depot;

    private ActionReservation(View supprimerButton,
                              View collecterButton,
                              AlimentaireModele modele) {
        this.collecterButton = collecterButton;
        this.collecterButton.setOnClickListener(this);
        this.supprimerButton = supprimerButton;
        this.supprimerButton.setOnClickListener(this);
        this.context = supprimerButton.getContext();
        this.modele = modele;
        this.depot = DepotManager.getInstance().getAlimentaireModeleDepot();
    }

    private static ActionReservation instancier(@NonNull View supprimerButton,
                                                @NonNull View collecterButton,
                                                @NonNull AlimentaireModele modele) {
        return new ActionReservation(supprimerButton, collecterButton, modele);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                dialog.dismiss();
                break;
            default:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(this.collecterButton)) {
            this.afficherDialog(R.string.msg_reservation_confirme_collecte);
        } else {
            this.afficherDialog(R.string.msg_reservation_confirme_suppression);
        }

    }

    private void afficherDialog(@StringRes int message) {
        new AlertDialog.Builder(this.context).setMessage(message)
                                             .setPositiveButton(R.string.bouton_confirme_oui, this)
                                             .setNegativeButton(R.string.bouton_confirme_non, this)
                                             .create()
                                             .show();
    }
}
