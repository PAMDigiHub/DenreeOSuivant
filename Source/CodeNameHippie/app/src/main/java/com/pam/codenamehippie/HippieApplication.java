package com.pam.codenamehippie;

import android.app.Application;

import com.pam.codenamehippie.http.Authentificateur;
import com.pam.codenamehippie.http.intercepteur.AcceptJsonInterceptor;
import com.pam.codenamehippie.http.intercepteur.HttpDebugInterceptor;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.OrganismeModeleDepot;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;
import com.pam.codenamehippie.modele.depot.UtilisateurModeleDepot;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Sous classe de {@link Application} qui sert à initialiser l'application et à stocker les
 * variables globales
 */
public class HippieApplication extends Application {

    public static final HttpUrl baseUrl =
            HttpUrl.parse("http://yolainecourteau.com/hippie/laravel/public/");
    /**
     * Instance du client http pour l'application
     */
    private volatile OkHttpClient httpClient;
    /**
     * Instance de {@link UtilisateurModeleDepot} pour l'application
     */
    private volatile UtilisateurModeleDepot utilisateurModeleDepot;

    /**
     * Instance d'{@link OrganismeModeleDepot} pour l'application
     */
    private volatile OrganismeModeleDepot organismeModeleDepot;
    /**
     * Instance de {@link TransactionModeleDepot} pour l'application
     */
    private volatile TransactionModeleDepot transactionModeleDepot;
    /**
     * Instance de {@link AlimentaireModeleDepot} pour l'application
     */
    private volatile AlimentaireModeleDepot alimentaireModeleDepot;

    public synchronized OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    public synchronized UtilisateurModeleDepot getUtilisateurModeleDepot() {
        if (this.utilisateurModeleDepot == null) {
            this.utilisateurModeleDepot = new UtilisateurModeleDepot(this, this.httpClient);
        }
        return this.utilisateurModeleDepot;
    }

    public synchronized OrganismeModeleDepot getOrganismeModeleDepot() {
        if (this.organismeModeleDepot == null) {
            this.organismeModeleDepot = new OrganismeModeleDepot(this, this.httpClient);
        }
        return this.organismeModeleDepot;
    }

    public synchronized TransactionModeleDepot getTransactionModeleDepot() {
        if (this.transactionModeleDepot == null) {
            this.transactionModeleDepot = new TransactionModeleDepot(this, this.httpClient);
        }
        return this.transactionModeleDepot;
    }

    public synchronized AlimentaireModeleDepot getAlimentaireModeleDepot() {
        if (this.alimentaireModeleDepot == null) {
            this.alimentaireModeleDepot = new AlimentaireModeleDepot(this, this.httpClient);
        }
        return this.alimentaireModeleDepot;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Configuration du client Http.
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.authenticator(Authentificateur.newInstance(this))
                         .addNetworkInterceptor(AcceptJsonInterceptor.newInstance());
        if (BuildConfig.DEBUG) {
            // Rapport de debug pour les requêtes.
            httpClientBuilder.addNetworkInterceptor(new HttpDebugInterceptor());
        }
        this.httpClient = httpClientBuilder.build();

    }
}
