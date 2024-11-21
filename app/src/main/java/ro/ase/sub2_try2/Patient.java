package ro.ase.sub2_try2;

import java.io.Serializable;

public class Patient implements Serializable {
    private String patientName;
    private float examinationCost;
    private boolean inssurance;

    public Patient(String patientName, float examinationCost, boolean inssurance) {
        setPatientName(patientName);
        setExaminationCost(examinationCost);
        setInssurance(inssurance);
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        if (patientName == null || patientName.isEmpty()) {
            throw new RuntimeException("name is invalid");
        }
        this.patientName = patientName;
    }

    public float getExaminationCost() {
        return examinationCost;
    }

    public void setExaminationCost(float examinationCost) {
        if (examinationCost < 0) {
            throw new RuntimeException("examination cost is invalid");
        }
        this.examinationCost = examinationCost;
    }

    public boolean isInssurance() {
        return inssurance;
    }

    public void setInssurance(boolean inssurance) {
        this.inssurance = inssurance;
    }

    @Override
    public String toString() {
        String inssured;
        if (inssurance) {
            inssured = "Inssured";
        } else {
            inssured = "Not Inssured";
        }
        return patientName + " | " + examinationCost + " | " + inssured;
    }
}
