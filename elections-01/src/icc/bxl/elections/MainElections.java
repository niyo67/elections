package icc.bxl.elections;

import java.util.Scanner;

public class MainElections {

	public static void main(String[] args) {

		// D�claration variable

		Scanner sc = new Scanner(System.in);
		boolean saisieOk;
		int nbSi�geAPourvoir = 0, nbVoixUtiles, i, nbSi�gesPourvus, iMax = 0, iSi�ge, nbListes = 0;
		double totalVoix;
		int[] voixListe, si�geListe;
		String[] nomListe;
		boolean[] elimineListe;
		double[] moyenneListe;
		double quotientElectoral, moyenneMax;

		// saisir le nombre de si�ges � pourvoir

		saisieOk = false;
		while ( !saisieOk ) {
			System.out.println("Entrez le nombre de si�ges � pourvoir : ");
			try {
				nbSi�geAPourvoir = sc.nextInt();

				if( nbSi�geAPourvoir < 0 ) {
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

		// saisir le nombre de liste en comp�tition

		saisieOk = false;
		while ( !saisieOk ) {
			System.out.println("Entrez le nombre de Liste en comp�tition : ");
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
		si�geListe = new int[nbListes];
		moyenneListe = new double[nbListes];

		//saisie des noms et voix des listes

		totalVoix = 0;
		for(i = 0; i < nbListes; i++) {
			saisieOk = false;
			while ( !saisieOk ) {
				System.out.println(" Nom de la liste n�" + (i+1) +  " : " );
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

		// 	V�rification des listes non �limin�es

		if ( nbVoixUtiles == 0 ) {
			System.out.println(" Erreur: toutes les listes ont �t� elimin�es");
			System.exit(1);
		}
		// repartition des si�ges au quotient

		quotientElectoral = nbVoixUtiles / nbSi�geAPourvoir;
		nbSi�gesPourvus = 0;
		for ( i = 0; i < nbListes; i++) {
			if(elimineListe[i] == false) {
				si�geListe[i] = (int) (voixListe[i] / quotientElectoral);
				moyenneListe[i] = voixListe[i] / (si�geListe[i] + 1);
				nbSi�gesPourvus = nbSi�gesPourvus + si�geListe[i];
			}else {
				si�geListe[i] = 0;
			}
		}

		//repartition des si�ges restants � la plus forte moyenne
		// 1 si�ge est attribu� � chaque tour de boucle

		for ( iSi�ge = 0; iSi�ge <= (nbSi�geAPourvoir - nbSi�gesPourvus - 1); iSi�ge++) {

			moyenneMax = -1;
			for( i =  0; i < nbListes; i++) {
				if(elimineListe[i] == false) {
					if( moyenneListe[i] > moyenneMax) {
						moyenneMax = moyenneListe[i];
						iMax = i;
					}
				}
			}
			si�geListe[iMax] = si�geListe[iMax] + 1;
			moyenneListe[iMax] = voixListe[iMax]/(si�geListe[iMax] + 1);
		}

		//affichage r�sultat sans tri

		for(i = 0; i < nbListes; i++) {
			if(elimineListe[i]) {
				System.out.println("La liste "+ nomListe[i]+ " a �t� �limin�e");

			} else {
				System.out.println( "La liste " + nomListe[i]+ " a obtenu [" +si�geListe[i]+ "] si�ge(s)");
			}
		}
		sc.close();


	}

}

