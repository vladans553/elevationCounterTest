package utils;

import utils.TestDataManager.MyVariable;

public class TestDataManager {
	
	
	
	
	public static String getGitHubVariable(MyVariable variable) {
		
        String githubValue = System.getenv(variable.getKeyName());       
        if (githubValue == null || githubValue.trim().isEmpty()) {
            throw new IllegalStateException(
                "GRESKA: Varijabla [" + variable.getKeyName() + "] nije pronađena na GitHub Actions-u! " +
                "Proveri da li si je dodao u .yml fajl unutar 'env:' sekcije."
            );
        }
        
        return githubValue;
    }

	
	
	
	


	public enum MyVariable {
	    
		VALID_PASSWORD("VALID_PASSWORD"),
		VALID_USERNAME("VALID_USERNAME");

	    private final String keyName;

	    
	    MyVariable(String keyName) {
	        this.keyName = keyName;
	    }

	    
	    public String getKeyName() {
	        return keyName;
	    }
	}
}
