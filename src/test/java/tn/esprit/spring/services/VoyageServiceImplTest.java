package tn.esprit.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Voyage;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.repository.VoyageRepository;
import tn.esprit.spring.services.VoyageServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class VoyageServiceImplTest {

    @Mock
    private VoyageRepository voyageRepository;

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private VoyageServiceImpl voyageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void ajouterVoyage_shouldSaveVoyage() {
        Voyage voyage = new Voyage();

        voyageService.ajouterVoyage(voyage);

        verify(voyageRepository, times(1)).save(voyage);
    }

    @Test
    void modifierVoyage_shouldSaveVoyage() {
        Voyage voyage = new Voyage();

        voyageService.modifierVoyage(voyage);

        verify(voyageRepository, times(1)).save(voyage);
    }

    @Test
    void affecterTrainAVoyage_shouldSetTrainForVoyage() {
        Long idTrain = 1L;
        Long idVoyage = 1L;
        Train train = new Train();
        Voyage voyage = new Voyage();

        when(trainRepository.findById(idTrain)).thenReturn(Optional.of(train));
        when(voyageRepository.findById(idVoyage)).thenReturn(Optional.of(voyage));

        voyageService.affecterTrainAVoyage(idTrain, idVoyage);

        Assertions.assertEquals(train, voyage.getTrain());
        verify(voyageRepository, times(1)).save(voyage);
    }

    @Test
    void recupererAll_shouldReturnAllVoyages() {
        List<Voyage> voyages = new ArrayList<>();
        voyages.add(new Voyage());
        voyages.add(new Voyage());

        when(voyageRepository.findAll()).thenReturn(voyages);

        List<Voyage> result = voyageService.recupererAll();

        Assertions.assertEquals(2, result.size());
    }

}
