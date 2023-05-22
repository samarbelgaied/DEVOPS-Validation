package tn.esprit.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Voyageur;
import tn.esprit.spring.repository.VoyageurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class VoyageurServiceImplTest {

    @Mock
    private VoyageurRepository voyageurRepository;

    @InjectMocks
    private VoyageurServiceImpl voyageurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void ajouterVoyageur_shouldSaveVoyageur() {
        Voyageur voyageur = new Voyageur();

        voyageurService.ajouterVoyageur(voyageur);

        verify(voyageurRepository, times(1)).save(voyageur);
    }

    @Test
    void modifierVoyageur_shouldSaveVoyageur() {
        Voyageur voyageur = new Voyageur();

        voyageurService.modifierVoyageur(voyageur);

        verify(voyageurRepository, times(1)).save(voyageur);
    }

    @Test
    void recupererAll_shouldReturnAllVoyageurs() {
        List<Voyageur> voyageurs = new ArrayList<>();
        voyageurs.add(new Voyageur());
        voyageurs.add(new Voyageur());

        when(voyageurRepository.findAll()).thenReturn(voyageurs);

        List<Voyageur> result = voyageurService.recupererAll();

        Assertions.assertEquals(2, result.size());
    }


    @Test
    void supprimerVoyageur_shouldCallVoyageurRepositoryDeleteMethod() {
        Voyageur voyageur = new Voyageur();

        voyageurService.supprimerVoyageur(voyageur);

        verify(voyageurRepository, times(1)).delete(voyageur);
    }


}
