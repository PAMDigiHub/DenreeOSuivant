package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.modele.MarchandiseModeleDepot;
import com.pam.codenamehippie.ui.adapter.HippieSpinnerAdapter;

public class AjoutMarchandiseActivity extends HippieActivity
        implements ValidateurObserver, AdapterView.OnItemSelectedListener {

    private ValidateurDeChampTexte validateurNom;
    private boolean nomEstValide;
    private ValidateurDeChampTexte validateurDescription;
    private boolean descriptionEstValide;
    private ValidateurDeChampTexte validateurQuantite;
    private boolean quantiteEstValide;
    private ValidateurDeChampTexte validateurValeur;
    private boolean valeurEstValide;
    private Spinner spinnerUniteMarchandise;
    private Spinner spinnerTypeMarchandise;
    private DatePicker datePeremption;
    private Button bAjoutMarchandise;
    private boolean spinnerUniteMarchandiseEstValide;
    private boolean spinnerTypeMarchandiseEstValide;
    private boolean datePeremptionEstValide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ajout_marchandise);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        EditText etNomMarchandise = (EditText) this.findViewById(R.id.etNomMarchandise);
        this.validateurNom =
                ValidateurDeChampTexte.newInstance(this,
                                                   etNomMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .NOM_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurNom.registerObserver(this);
        EditText etDescMarchandise = (EditText) this.findViewById(R.id.etDescMarchandise);
        this.validateurDescription =
                ValidateurDeChampTexte.newInstance(this,
                                                   etDescMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .DESCRIPTION_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurDescription.registerObserver(this);
        EditText etQteeMarchandise = (EditText) this.findViewById(R.id.etQteeMarchandise);
        this.validateurQuantite =
                ValidateurDeChampTexte.newInstance(this,
                                                   etQteeMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .QUANTITE_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurQuantite.registerObserver(this);
        this.spinnerUniteMarchandise = (Spinner) this.findViewById(R.id.spinnerUniteMarchandise);
        //TODO: Plugger les données du service WEB
        MarchandiseModeleDepot marchandiseModeleDepot =
                ((HippieApplication) this.getApplication()).getMarchandiseModeleDepot();

        HippieSpinnerAdapter uniteAdapter =
                new HippieSpinnerAdapter(this, marchandiseModeleDepot.getListeUnitee());
        this.spinnerUniteMarchandise.setAdapter(uniteAdapter);
        EditText etValeurMarchandise = (EditText) this.findViewById(R.id.etValeurMarchandise);
        this.validateurValeur =
                ValidateurDeChampTexte.newInstance(this,
                                                   etValeurMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .VALEUR_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurValeur.registerObserver(this);
        this.spinnerTypeMarchandise = (Spinner) this.findViewById(R.id.spinnerTypeMarchandise);
        //TODO: Plugger les données du WEB
        HippieSpinnerAdapter typeAdapter =
                new HippieSpinnerAdapter(this, marchandiseModeleDepot.getListeTypeAlimentaire());
        this.spinnerTypeMarchandise.setAdapter(typeAdapter);
        this.datePeremption = (DatePicker) this.findViewById(R.id.datePicker);
        this.bAjoutMarchandise = (Button) this.findViewById(R.id.bAjoutMarchandise);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.validateurNom.onPause();
        this.validateurDescription.onPause();
        this.validateurQuantite.onPause();
        this.validateurValeur.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.validateurNom.onResume();
        this.validateurDescription.onResume();
        this.validateurQuantite.onResume();
        this.validateurValeur.onResume();
    }

    @Override
    public void enValidatant(Validateur validateur, boolean estValide) {
        if (validateur.equals(this.validateurNom)) {
            this.nomEstValide = estValide;
        } else if (validateur.equals(this.validateurDescription)) {
            this.descriptionEstValide = estValide;
        } else if (validateur.equals(this.validateurQuantite)) {
            this.quantiteEstValide = estValide;
        }
        if (validateur.equals(this.validateurValeur)) {
            this.valeurEstValide = estValide;
        } else if (this.spinnerUniteMarchandise.getSelectedItemId() != 0) {
            this.spinnerUniteMarchandiseEstValide = estValide;
        } else if (this.spinnerTypeMarchandise.getSelectedItemId() != 0) {
            this.spinnerTypeMarchandiseEstValide = estValide;
        }
        //TODO: Valider datePeremption si egal date du jour mettre datePeremption à null sinon
        // convertir au bon format et mettre datePeremptionEstValide = estValide
        this.bAjoutMarchandise.setEnabled(this.nomEstValide &&
                                          this.descriptionEstValide &&
                                          this.quantiteEstValide &&
                                          this.valeurEstValide &&
                                          this.spinnerUniteMarchandiseEstValide &&
                                          this.spinnerTypeMarchandiseEstValide);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
