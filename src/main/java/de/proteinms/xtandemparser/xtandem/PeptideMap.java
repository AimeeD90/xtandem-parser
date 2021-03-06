package de.proteinms.xtandemparser.xtandem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class holds the peptide information in a map.
 *
 * @author Thilo Muth
 */
public class PeptideMap implements Serializable {

    /**
     * This variable holds the first dimension hash map for the peptide hash map
     * as value.
     */
    private HashMap<String, HashMap<String, Peptide>> iSpectrumAndPeptideMap = null;

    /**
     * Builds the peptide map.
     *
     * @param aRawPeptideMap the raw peptide map
     * @param aProteinMap the protein map
     * @param aNumberOfSpectra the number of spectra
     */
    public PeptideMap(HashMap aRawPeptideMap, ProteinMap aProteinMap, int aNumberOfSpectra) {
        buildPeptideMap(aRawPeptideMap, aProteinMap, aNumberOfSpectra);
    }

    /**
     * Constructs the 2-dim hash map, the first dimension is the map with the
     * spectrum-number as key and another hash map as value. The second
     * dimension has the peptideID (e.g. s171_p2 for spectrum number 171 and the
     * second peptide) and the peptide object as value.
     *
     * @param aRawPeptideMap the raw peptide map
     * @param aProteinMap the protein map
     * @param aNumberOfSpectra the number of spectra
     * @return the spectrum and peptides map
     */
    private HashMap buildPeptideMap(HashMap aRawPeptideMap, ProteinMap aProteinMap, int aNumberOfSpectra) {

        // First dimension of the map, which contains the spectra as key and the peptide hash maps as values
        iSpectrumAndPeptideMap = new HashMap(aNumberOfSpectra);

        if (aRawPeptideMap != null) {
            for (int i = 1; i <= aNumberOfSpectra; i++) {

                // Hashmap for the peptide objects
                HashMap<String, Peptide> lPeptideMap = new HashMap<String, Peptide>();

                // The counter for the peptides
                int pCount = 1;

                // Check if there are any values given out of the map
                while (aRawPeptideMap.get("s" + i + "_p" + pCount) != null) {

                    // The peptide id is consists of s + spectrum# + _p + peptide#
                    String peptideID = ("s" + i + "_p" + pCount);
                    int peptideStart = 0, peptideEnd = 0;
                    Object input = aRawPeptideMap.get("start" + "_s" + i + "_p" + pCount);
                    if (input != null) {
                        peptideStart = new Integer(input.toString());
                    }
                    input = aRawPeptideMap.get("start" + "_s" + i + "_p" + pCount);
                    if (input != null) {
                        peptideEnd = new Integer(input.toString());
                    }
                    input = aRawPeptideMap.get("seq" + "_s" + i + "_p" + pCount);
                    String sequence = "";
                    if (input != null) {
                        sequence = aRawPeptideMap.get("seq" + "_s" + i + "_p" + pCount).toString().trim();
                    }

                    // Create an instance of a peptide.
                    Peptide peptide = new Peptide(peptideID, peptideStart, peptideEnd, sequence);
                    // Set the domain values
                    peptide.setSpectrumNumber(i);
                    // set the fasta filename
                    if (aRawPeptideMap.get("URL" + "_s" + i + "_p" + pCount) != null) {
                        peptide.setFastaFilePath(aRawPeptideMap.get("URL" + "_s" + i + "_p" + pCount).toString());
                    }

                    // The counter for the domains
                    int dCount = 1;

                    // List of the domains
                    List<Domain> domainList = new ArrayList<Domain>();
                    while (aRawPeptideMap.get("domainid" + "_s" + i + "_p" + pCount + "_d" + dCount) != null) {
                        Domain domain = new Domain();
                        String domainKey = "s" + i + "_p" + pCount + "_d" + dCount;
                        domain.setDomainKey(domainKey);
                        domain.setDomainID(aRawPeptideMap.get("domainid" + "_s" + i + "_p" + pCount + "_d" + dCount).toString());
                        input = aRawPeptideMap.get("proteinkey" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setProteinKey(input.toString());
                        }
                        input = aRawPeptideMap.get("domainstart" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainStart(Integer.parseInt(input.toString()));
                        }
                        input = aRawPeptideMap.get("domainend" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainEnd(Integer.parseInt(input.toString()));
                        }
                        input = aRawPeptideMap.get("expect" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainExpect(Double.parseDouble(input.toString()));
                        }
                        input = aRawPeptideMap.get("mh" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainMh(Double.parseDouble(input.toString()));
                        }
                        input = aRawPeptideMap.get("delta" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainDeltaMh(Double.parseDouble(input.toString()));
                        }
                        input = aRawPeptideMap.get("hyperscore" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainHyperScore(Double.parseDouble(input.toString()));
                        }
                        input = aRawPeptideMap.get("nextscore" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainNextScore(Double.parseDouble(input.toString()));
                        }
                        input = aRawPeptideMap.get("pre" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setUpFlankSequence(input.toString());
                        }
                        input = aRawPeptideMap.get("post" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDownFlankSequence(input.toString());
                        }
                        input = aRawPeptideMap.get("domainseq" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setDomainSequence(input.toString());
                        }
                        input = aRawPeptideMap.get("missed_cleavages" + "_s" + i + "_p" + pCount + "_d" + dCount);
                        if (input != null) {
                            domain.setMissedCleavages(Integer.parseInt(input.toString()));
                        }
                        domainList.add(domain);
                        dCount++;
                    }

                    // Set the domains for the peptide
                    peptide.setDomains(domainList);

                    // Put the peptide into the map, value is the domain id.
                    lPeptideMap.put(peptideID, peptide);
                    pCount++;
                }
                iSpectrumAndPeptideMap.put("s" + i, lPeptideMap);
            }
        }
        return iSpectrumAndPeptideMap;
    }

    /**
     * Returns the 2-dim spectrum and peptide map.
     *
     * @return iSpectrumAndPeptideMap HashMap
     */
    public HashMap<String, HashMap<String, Peptide>> getSpectrumAndPeptideMap() {
        return iSpectrumAndPeptideMap;
    }

    /**
     * Retrieve all possible peptide objects for a given spectrum. /!\ This list
     * is not indexed according to the index of the peptide!
     *
     * @param aSpectrumNumber the spectrum number
     * @return peptideList ArrayList
     */
    public ArrayList<Peptide> getAllPeptides(int aSpectrumNumber) {

        ArrayList<Peptide> arrayList = new ArrayList<Peptide>();

        if (iSpectrumAndPeptideMap.get("s" + aSpectrumNumber) != null) {
            arrayList = new ArrayList<Peptide>(iSpectrumAndPeptideMap.get("s" + aSpectrumNumber).values());
        }

        return arrayList;
    }

    /**
     * Returns a specific peptide by an index.
     *
     * @param aSpectrumNumber the spectrum number
     * @param index the index at which the peptide occurs in the xml file
     * @return peptide Peptide the desired peptide
     */
    public Peptide getPeptideByIndex(int aSpectrumNumber, int index) {
        return iSpectrumAndPeptideMap.get("s" + aSpectrumNumber).get("s" + aSpectrumNumber + "_p" + index);
    }

    /**
     * Returns the number of peptides for a given spectrum
     *
     * @param aSpectrumNumber the spectrum number
     * @return The total number of peptides
     */
    public int getNumberOfPeptides(int aSpectrumNumber) {
        return iSpectrumAndPeptideMap.get("s" + aSpectrumNumber).size();
    }
}
