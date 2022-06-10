package br.gov.sp.fatec.springbootapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@SpringBootTest
@Transactional
class SpringBootAppApplicationTests {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private AutorizacaoRepository autRepo;

	@Autowired
	private SegurancaService segService;

	@Test
	void contextLoads() {
	}

@BeforeAll
	static void init(@Autowired JdbcTemplate jdbcTemplate){
		jdbcTemplate.update(
			"insert into usr_usuario(usr_nome, usr_senha) values(?,?)",
				"Luara", "Senh4zona");
		jdbcTemplate.update(
			"insert into aut_autorizacao (aut_nome) values(?)",
					"ROLE_ADMIN");
		jdbcTemplate.update(
			"insert into uau_usuario_autorizacao (usr_id, aut_id) values(?,?)",
				1L, 1L);
	}

	@Test
	void testaInsercao(){
		Usuario usuario = new Usuario();
		usuario.setNome("luara");
		usuario.setSenha("Senh4zona");
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		Autorizacao aut = new Autorizacao();
		aut.setNome("ROLE_USUARIO");
		autRepo.save(aut);
		usuario.getAutorizacoes().add(aut);
		usuarioRepo.save(usuario);
		assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
	}

	@Test
	void testaInsercaoAutorizacao(){
		Usuario usuario = new Usuario();
		usuario.setNome("luara");
		usuario.setSenha("Senh4zona");
		usuarioRepo.save(usuario);
		Autorizacao aut = new Autorizacao();
		aut.setNome("ROLE_USUARIO2");
		aut.setUsuarios(new HashSet<Usuario>());
		aut.getUsuarios().add(usuario);
		autRepo.save(aut);
		assertNotNull(aut.getUsuarios().iterator().next().getId());
	}


	@Test
	void testaAutorizacao(){
		Usuario usuario = usuarioRepo.findById(1L).get();

		assertEquals("ROLE_ADMIN", usuario.getAutorizacoes().iterator().next().getNome());
	}
	@Test
	void testaUsuario(){
		Autorizacao aut = autRepo.findById(1L).get();
		assertEquals("Luara", aut.getUsuarios().iterator().next().getNome());
	}

	@Test
	void testaBuscaUsuarioNomeContains(){
		List<Usuario> usuarios = usuarioRepo.findByNomeContainsIgnoreCase("E");
		assertFalse(usuarios.isEmpty());
	}

	@Test
	void testaBuscaUsuarioNome(){
		Usuario usuario = usuarioRepo.findByNome("luara");
		assertNotNull(usuario);
	}

	@Test
	void testaBuscaUsuarioNomeQuery(){
		Usuario usuario = usuarioRepo.findByNome("luara");
		assertNotNull(usuario);
	}

	@Test
	void testaBuscaUsuarioNomeSenha(){
		Usuario usuario = usuarioRepo.findByNomeAndSenha("luara", "Senh4zona");
		assertNotNull(usuario);
	}

	@Test
	void testaBuscaUsuarioNomeSenhaQuery(){
		Usuario usuario = usuarioRepo.buscaUsuarioPorNomeESenha("luara", "Senh4zona");
		assertNotNull(usuario);
	}


	
	@Test
	void testaBuscaUsuarioNomeAutorizacao(){
		List<Usuario> usuarios = usuarioRepo.findByAutorizacoesNome("ROLE_ADMIN");
		assertFalse(usuarios.isEmpty());
	}
	
	@Test
	void testaBuscaUsuarioNomeAutorizacaoQuery(){
		List<Usuario> usuarios = usuarioRepo.findByAutorizacoesNome("ROLE_ADMIN");
		assertFalse(usuarios.isEmpty());
	}

	@Test
	void testaServicoCriaUsuario(){
		Usuario usuario = segService.criarUsuario("user", "adm123", "ROLE_USUARIO");
		assertNotNull(usuario);
	}
}
