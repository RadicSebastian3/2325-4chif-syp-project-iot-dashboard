package at.htl.leoenergy.controller;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped

public class CounterBean {

    @ConfigProperty(name = "json.file-directory-midi")
    private String directoryNameMidi;

    @ConfigProperty(name = "json.file-directory-maxi_100")
    private String directoryNameMaxi_100;

    @ConfigProperty(name = "json.file-directory-all")
    private String directoryNameAll;

    @ConfigProperty(name = "json.file-directory-midi_700")
    private String directoryNameMidi_700;

    @Inject
    private FileProcessorHelper fileProcessorHelper;
    private AtomicInteger counter = new AtomicInteger();

    public int get() {
        return counter.get();
    }


  @Scheduled(every="5s")
    void increment() {
        fileProcessorHelper.importJsonFiles(directoryNameAll,20);
        //LIMIT for the datas how many it should read at once
    }

}
