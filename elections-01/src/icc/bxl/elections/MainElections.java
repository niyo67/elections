package icc.bxl.elections;

import java.util.Scanner;

public class MainElections {

	public static void main(String[] args) {

		// Déclaration variable

		Scanner sc = new Scanner(System.in);
		boolean saisieOk;
		int nbSiègeAPourvoir = 0, nbVoixUtiles, i, nbSiègesPourvus, iMax = 0, iSiège, nbListes = 0;
		double totalVoix;
		int[] voixListe, siègeListe;
		String[] nomListe;
		boolean[] elimineListe;
		double[] moyenneListe;
		double quotientElectoral, moyenneMax;

		// saisir le nombre de sièges à pourvoir

		saisieOk = false;
		while ( !saisieOk ) {
			System.out.println("Entrez le nombre de sièges à pourvoir : ");
			try {
				nbSiègeAPourvoir = sc.nextInt();

				if( nbSiègeAPourvoir < 0 ) {
					System.out.println("Erreur : tapez un nombre entier > 0");
				}else {
					saisieOk = true;
				}

			}catch(Exception e) {
				System.out.println("Erreur : tapez un nombre entier > 0");
				sc.next();
				continue;
			}

		}

		// saisir le nombre de liste en compétition

		saisieOk = false;
		while ( !saisieOk ) {
			System.out.println("Entrez le nombre de Liste en compétition : ");
			try{
				nbListes = sc.nextInt();
				if( nbListes < 0 ) {
					System.out.println(" Erreur : tapez un nombre entier > 0");
				}else {
					saisieOk = true;
				}

			}catch(Exception e) {
				System.out.println(" Erreur : tapez un nombre entier > 0");
				sc.next();
				continue;
			}


		}

		//dimensionnement des tableaux
		nomListe = new String[nbListes];
		voixListe = new int[nbListes];
		elimineListe = new boolean[nbListes];
		siègeListe = new int[nbListes];
		moyenneListe = new double[nbListes];

		//saisie des noms et voix des listes

		totalVoix = 0;
		for(i = 0; i < nbListes; i++) {
			saisieOk = false;
			while ( !saisieOk ) {
				System.out.println(" Nom de la liste n°" + (i+1) +  " : " );
				try{
					nomListe[i] = sc.next();
					if( nomListe[i] == null ) {
						System.out.println(" Erreur : tapez un nom non vide");
					}else {
						saisieOk = true;
					}
				}catch(Exception e) {
					System.out.println(" Erreur : tapez un nom non vide");
					sc.next();
					continue;
				}

			}

			saisieOk = false;
			while ( !saisieOk ) {
				System.out.println("Entrez le nombre de voix de la liste " + nomListe[i]+ " :");
				try{
					voixListe[i] = sc.nextInt();
					if (voixListe[i] < 0){
						System.out.println("Erreur: tapez un nombre entier >=0");
					}else {
						saisieOk = true;
					}

				}catch(Exception e) {
					System.out.println("Erreur: tapez un nombre entier >=0");
					sc.next();
					continue;
				}

			}





			totalVoix = totalVoix + voixListe[i];

		}

		// Calcul des voix utiles

		nbVoixUtiles = 0;

		for(i = 0; i < nbListes; i++) {

			if(( voixListe[i] / totalVoix) < 0.05 ) {
				elimineListe[i] = true;
			} else {
				elimineListe[i]= false;
				nbVoixUtiles = nbVoixUtiles + voixListe[i];
			}
		}

		// 	Vérification des listes non éliminées

		if ( nbVoixUtiles == 0 ) {
			System.out.println(" Erreur: toutes les listes ont été eliminées");
			System.exit(1);
		}
		// repartition des sièges au quotient

		quotientElectoral = nbVoixUtiles / nbSiègeAPourvoir;
		nbSiègesPourvus = 0;
		for ( i = 0; i < nbListes; i++) {
			if(elimineListe[i] == false) {
				siègeListe[i] = (int) (voixListe[i] / quotientElectoral);
				moyenneListe[i] = voixListe[i] / (siègeListe[i] + 1);
				nbSiègesPourvus = nbSiègesPourvus + siègeListe[i];
			}else {
				siègeListe[i] = 0;
			}
		}

		//repartition des sièges restants à la plus forte moyenne
		// 1 siége est attribué à chaque tour de boucle

		for ( iSiège = 0; iSiège <= (nbSiègeAPourvoir - nbSiègesPourvus - 1); iSiège++) {

			moyenneMax = -1;
			for( i =  0; i < nbListes; i++) {
				if(elimineListe[i] == false) {
					if( moyenneListe[i] > moyenneMax) {
						moyenneMax = moyenneListe[i];
						iMax = i;
					}
				}
			}
			siègeListe[iMax] = siègeListe[iMax] + 1;
			moyenneListe[iMax] = voixListe[iMax]/(siègeListe[iMax] + 1);
		}

		//affichage résultat sans tri

		for(i = 0; i < nbListes; i++) {
			if(elimineListe[i]) {
				System.out.println("La liste "+ nomListe[i]+ " a été éliminée");

			} else {
				System.out.println( "La liste " + nomListe[i]+ " a obtenu [" +siègeListe[i]+ "] siège(s)");
			}
		}
		sc.close();


	}

}

