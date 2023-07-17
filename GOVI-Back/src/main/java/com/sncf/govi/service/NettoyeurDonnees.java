package com.sncf.govi.service;

import com.sncf.govi.configuration.InfoColonnesRATPProvider;
import com.sncf.govi.configuration.InfoColonnesBHLProvider;
import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.model.Gare;
import com.sncf.govi.service.model.Quai;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NettoyeurDonnees {

    private final InfoColonnesRATPProvider infoColonnesRATPProvider;
    private final InfoColonnesBHLProvider infoColonnesBHLProvider;

    public static boolean isAlpha(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public List<Gare> retraitGaresEtQuaisVides(List<Gare> listeGares){
        // Ces variables tampons nous permettent de modifier celles qu'on itère sans générer d'erreurs
        List<Quai> newListeQuais;
        List<Gare> newListeGares = new ArrayList<>();

        // On parcourt toutes les gares
        for(Gare gare : listeGares){

            newListeQuais = gare.getQuais();

            // On retire les quais qui n'ont pas de retournements
            newListeQuais.removeIf(quai -> quai.getRetournements().isEmpty());

            // On garde les Gares dont tous les quais ne sont pas vides
            if(!newListeQuais.isEmpty()){
                gare.setQuais(newListeQuais);
                newListeGares.add(gare);
            }
        }

        return(newListeGares);
    }

    public Workbook formatRATPVersFormatBHL(Workbook fichierRATP){

        Workbook fichierRATPConverti = new XSSFWorkbook();
        Sheet feuilleBHL = fichierRATPConverti.createSheet("RATPtoBHL");

        Sheet feuilleRATP = fichierRATP.getSheetAt(0);

        boolean firstRow = true;
        boolean aInscrire = false;

        List<String> ligneRATP = new ArrayList<>(Collections.nCopies(33, null));
        List<String> ligneBHL = new ArrayList<>(Collections.nCopies(33, null));

        // On l'initialise avec une manœuvre vide
        String manoeuvreActuelle = "";

        for (Row row : feuilleRATP) {

            // On donne ses nouvelles valeurs à notre ligne
            nettoyageListe(ligneRATP);
            fromLigneToList(row, ligneRATP);

            // On passe la première ligne du fichier, contenant juste le nom des colonnes et toutes les lignes sans manœuvre
            if (firstRow || ligneRATP.get(infoColonnesRATPProvider.getManoeuvre()).equals("-")) {
                firstRow = false;
                continue;
            }
            // Si on change de manœuvre, on réinitialise nos recherches, pour en commencer avec cette nouvelle
            if(!ligneRATP.get(infoColonnesRATPProvider.getManoeuvre()).equals(manoeuvreActuelle)){
                // Si la manœuvre précédemment construite
                if (aInscrire){
                    redigerNouvelleLigne(feuilleBHL,ligneBHL);
                }

                // On initialise notre bool permettant d'inscrire une nouvelle ligne de notre BHL basé sur la RATP
                aInscrire = false;

                nettoyageListe(ligneBHL);
                manoeuvreActuelle = ligneRATP.get(infoColonnesRATPProvider.getManoeuvre());
            }

            // Cas d'un retournement simple (mono voie) ATTENTION PASSE AUSSI ICI LES UM PASSANT US
            if(assignationTypeManoeuvre(ligneRATP.get(infoColonnesRATPProvider.getManoeuvre())).toUpperCase().equals(infoColonnesRATPProvider.getManoeuvreRetournement()) && assignationVoie(ligneRATP.get(infoColonnesRATPProvider.getManoeuvre())).length == 1){

                // Cas de la mission d'arrivée du retournement (toUpperCase pour s'épargner les erreurs entre Maj et minuscules)
                if(infoColonnesRATPProvider.getTypeEvenementArrivee().contains(ligneRATP.get(infoColonnesRATPProvider.getTypeEvenement()).toUpperCase())){

                // Enregistrement du code mission d'arrivée
                    ligneBHL.set(infoColonnesBHLProvider.getCodeMissionArrivee(),ligneRATP.get(infoColonnesRATPProvider.getCodeMission()));
                // Enregistrement des gares du train d'arrivée (sa gare d'arrivée et de départ)
                    ligneBHL.set(infoColonnesBHLProvider.getGaresTrainArrivee(),"null"+"/"+assignationGare(ligneRATP.get(infoColonnesRATPProvider.getManoeuvre())));
                // Enregistrement de l'heure d'arrivée du retournement
                    ligneBHL.set(infoColonnesBHLProvider.getHeureArrivee(),ligneRATP.get(infoColonnesRATPProvider.getHeureArrivee()));
                // Enregistrement de la seule voie du retournement
                    ligneBHL.set(infoColonnesBHLProvider.getNumVoie(), assignationVoie(ligneRATP.get(infoColonnesRATPProvider.getManoeuvre()))[0]);
                }

                // Cas de la mission de départ du retournement
                if(infoColonnesRATPProvider.getTypeEvenementDepart().contains(ligneRATP.get(infoColonnesRATPProvider.getTypeEvenement()).toUpperCase())){

                // Enregistrement du code mission de départ
                    ligneBHL.set(infoColonnesBHLProvider.getCodeMissionDepart(),ligneRATP.get(infoColonnesRATPProvider.getCodeMission()));
                // Enregistrement des gares du train de départ (sa gare de départ (l'actuelle) et celle d'arrivée (sa destination))
                    ligneBHL.set(infoColonnesBHLProvider.getGaresTrainDepart(),assignationGare(ligneRATP.get(infoColonnesRATPProvider.getManoeuvre()))+"/"+"null");
                // Enregistrement de l'heure d'arrivée du retournement
                    ligneBHL.set(infoColonnesBHLProvider.getHeureDepart(),ligneRATP.get(infoColonnesRATPProvider.getHeureDepart()));
                // Une fois l'aller et le retour du retournement saisi, on peut simplement enregistrer notre retournement
                    aInscrire = true;
                }
            }

            // Cas d'un retournement complexe (passage VZ, passage SAS)
            //...


        }

        // Une fois tout notre fichier RATP parcouru et converti, on le retourne
        return fichierRATPConverti;
    }

    private void nettoyageListe(List<String> ligne) {
        ligne.clear();
        ligne.addAll(Collections.nCopies(33, null));
    }

    private void fromLigneToList(Row row, List<String> ligneRATP){
        ligneRATP.set(infoColonnesRATPProvider.getCodeADC(), getStringValue(row.getCell(infoColonnesRATPProvider.getCodeADC())));
        ligneRATP.set(infoColonnesRATPProvider.getNumeroRame(), getStringValue(row.getCell(infoColonnesRATPProvider.getNumeroRame())));
        ligneRATP.set(infoColonnesRATPProvider.getCodeMission(), getStringValue(row.getCell(infoColonnesRATPProvider.getCodeMission())));
        ligneRATP.set(infoColonnesRATPProvider.getNumVoieArrivee(), getStringValue(row.getCell(infoColonnesRATPProvider.getNumVoieArrivee())));
        ligneRATP.set(infoColonnesRATPProvider.getNumVoieDepart(), getStringValue(row.getCell(infoColonnesRATPProvider.getNumVoieDepart())));
        ligneRATP.set(infoColonnesRATPProvider.getManoeuvre(), getStringValue(row.getCell(infoColonnesRATPProvider.getManoeuvre())));
        ligneRATP.set(infoColonnesRATPProvider.getTypeEvenement(), getStringValue(row.getCell(infoColonnesRATPProvider.getTypeEvenement())));
        ligneRATP.set(infoColonnesRATPProvider.getHeureArrivee(),getStringValue(row.getCell(infoColonnesRATPProvider.getHeureArrivee())));
        ligneRATP.set(infoColonnesRATPProvider.getHeureDepart(),getStringValue(row.getCell(infoColonnesRATPProvider.getHeureDepart())));

    }

    public static String getStringValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> cell.getLocalDateTimeCellValue().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            default -> "";
        };
    }
    private String[] decoupageManoeuvre(String manoeuvre) {
        // On garde trois voies ou plus si la manœuvre en contient trois, sinon on en retourne qu'une (la seule contenue dans la manœuvre)
        String[] voiesUtiles;

        // Les traits sont remplacés par des espaces, ainsi tous les séparateurs sont désormais des espaces
        manoeuvre = manoeuvre.replace("-"," ");
        // On fait en sorte que chaque espace ou secession d'espaces sépare notre chaine de caractère en éléments
        String[] manoeuvreSeparee = manoeuvre.split("\\s+");

        return(manoeuvreSeparee);
    }

    /**
     * Permet de récupérer la/les voie(s) de la manœuvre
     * @param manoeuvre est la manœuvre de laquelle extraire les informations
     * @return donne ensuite la/les voie(s) extraites
     */
    private String[] assignationVoie(String manoeuvre){

        // On retire l'alias gare et le type de manœuvre et on renvoie la chaine de caractère avec la/les voie(s)
        String[] voies = Arrays.copyOfRange(decoupageManoeuvre(manoeuvre), 2, decoupageManoeuvre(manoeuvre).length);

        return(voies);

    }

    /**
     * Permet de récupérer le type de manœuvre contenu dans la colonne manoeuvre du fichier RATP
     * @param manoeuvre est la manœuvre à analyser qu'il faut passer en paramètre
     * @return donne la lettre correspondant au type de maneouvre (R, P, D ou G)
     */
    private String assignationTypeManoeuvre(String manoeuvre){

        // On récupère le string contenant le type de manœuvre et on la retourne
        String[] manoeuvreSeparee = decoupageManoeuvre(manoeuvre);
        return(manoeuvreSeparee[1].trim());

    }

    /**
     * Permet de récupérer le type de manœuvre contenu dans la colonne manoeuvre du fichier RATP
     * @param manoeuvre est la manœuvre à analyser qu'il faut passer en paramètre
     * @return donne la lettre correspondant au type de maneouvre (R, P, D ou G)
     */
    private String assignationGare(String manoeuvre){

        // On récupère le string contenant le type de manœuvre et on la retourne
        String[] manoeuvreSeparee = decoupageManoeuvre(manoeuvre);
        return(manoeuvreSeparee[0].trim());

    }

    private void redigerNouvelleLigne(Sheet feuilleRATPConverti, List<String> donneesLignes){

        // Crée une nouvelle ligne à la fin de la feuille
        int rowCount = feuilleRATPConverti.getLastRowNum();
        Row row = feuilleRATPConverti.createRow(++rowCount);

        // Ajoute les données de la liste à la nouvelle ligne
        int columnCount = 0;
        Cell cell;

        // Chaque valeur de la liste ayant pour rang le numéro de la colonne BHL associée, le format final correspondra à celui du BHL
        for (String value : donneesLignes) {
            cell = row.createCell(columnCount++);
            cell.setCellValue(value);
        }
    }


    public Workbook nettoyageDonneesConducteurs(Workbook donneesConducteurs){

    //----------------------------------------------------------------------------------------------------------
    // étape 1 : suppression des trains avec mauvaise mission (ligne H,K...) ou nature (TAXI...)
    //----------------------------------------------------------------------------------------------------------
    Sheet sheet = donneesConducteurs.getSheetAt(0); // Accéder à la première feuille


        // Parcourir les lignes de la dernière à la première
        for (int i = sheet.getLastRowNum(); i >= 0; i--) {
            Row row = sheet.getRow(i);

            if (row != null) {
                Cell cell = row.getCell(0); // Accéder à la première cellule
                if (cell == null || cell.toString().length() != 6 || !isNumeric(cell.toString().substring(4)) || !isAlpha(cell.toString().substring(0, 4))) { // Tester le critère
                    // Si le critère ne correspond pas, supprimer la ligne
                    if (i != sheet.getLastRowNum()) {
                        sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                    }
                    sheet.removeRow(row);
                }
            }
        }


    //#----------------------------------------------------------------------------------------------------------
    //# étape 2 : Creation des ADC avec le format adequate
    //#----------------------------------------------------------------------------------------------------------

    //#----------------------------------------------------------------------------------------------------------
    //# étape 4 : Creation de la colonne PS/FS (Prise de service, Fin de Service)
    //#----------------------------------------------------------------------------------------------------------

    //#----------------------------------------------------------------------------------------------------------
    //# étape 5 : Creation et renseignement de la colonne relève GDS
    //#----------------------------------------------------------------------------------------------------------

        return (donneesConducteurs);
    }

    public Workbook fusionJ1J2(Workbook donneesJ1, Workbook donneesJ2, TypeFichierEnum typeFichierEnum) {
        // Créer un nouveau workbook pour les données fusionnées
        Workbook donneesFusionnees = new XSSFWorkbook();

        // Créer une nouvelle feuille de calcul dans le workbook fusionné
        Sheet sheetFusion = donneesFusionnees.createSheet("Données Fusionnées");

        // On saute la première ligne du second fichier BHL pour éviter que le nom des colonnes soit une deuxième
        // fois dans le fichier et au milieu de celui-ci
        boolean sauterPremiereLigne = typeFichierEnum.equals(TypeFichierEnum.BHL);

        // Parcourir les données dans le premier workbook et les copier dans le workbook fusionné
        copierDonneesWorkbook(donneesJ1, sheetFusion, false);

        // Parcourir les données dans le deuxième workbook et les copier dans le workbook fusionné
        copierDonneesWorkbook(donneesJ2, sheetFusion, sauterPremiereLigne);

        return (donneesFusionnees);
    }

    private void copierDonneesWorkbook(Workbook workbook, Sheet sheetFusion, boolean sauterPremiereLigne) {
        Sheet sheetSource = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheetSource.iterator();
        int rowCount = 0;
        if(sheetFusion.getLastRowNum() > 0) {
            rowCount = sheetFusion.getLastRowNum() + 1;
        }

        // Ignorer la première ligne, le cas échéant
        if (sauterPremiereLigne && rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row rowSource = rowIterator.next();
            Row rowFusion = sheetFusion.createRow(rowCount++);

            Iterator<Cell> cellIterator = rowSource.cellIterator();
            int cellCount = 0;
            while (cellIterator.hasNext()) {
                Cell cellSource = cellIterator.next();
                Cell cellFusion = rowFusion.createCell(cellCount++);
                switch (cellSource.getCellType()) {
                    case STRING -> cellFusion.setCellValue(cellSource.getStringCellValue());
                    case NUMERIC -> cellFusion.setCellValue(cellSource.getNumericCellValue());
                    case BOOLEAN -> cellFusion.setCellValue(cellSource.getBooleanCellValue());
                    case FORMULA -> cellFusion.setCellFormula(cellSource.getCellFormula());
                    default -> throw new IllegalStateException("Type inattendu: " + cellSource.getCellType());
                }
            }
        }
    }
}
