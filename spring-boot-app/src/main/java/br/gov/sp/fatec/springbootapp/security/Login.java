package br.gov.sp.fatec.springbootapp.security;

public class Login {
    private String username;

    private String password;

    private String autorizacao;

    private String token;
    
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username; 
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String username){
        this.password = password; 
    }

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

    public void setToken(String generateToken) {
    }
}
