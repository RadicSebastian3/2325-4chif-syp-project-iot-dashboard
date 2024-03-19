import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Graph} from "../model/Graph";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {HttpClient} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {Subscription, timer} from "rxjs";
import { switchMap } from 'rxjs/operators';
import {Duration} from "../model/Duration";
import {GraphComponent} from "../graph/graph.component";

@Component({
  selector: 'app-graph-overview',
  standalone: true,
  imports: [
    NgForOf,
    NgOptimizedImage,
    NgIf,
    FormsModule,
    MatSlideToggle,
    GraphComponent
  ],
  templateUrl: './graph-overview.component.html',
  styleUrl: './graph-overview.component.css'
})
export class GraphOverviewComponent{
  public constructor(public sanitizer: DomSanitizer, public http: HttpClient) {
    this.http.get<any>('assets/data/graph-data.json').subscribe((data) => {
      this.graphs = data;
      console.log(this.graphs.length + " graphs loaded");
      console.log(this.graphs);
      this.nextGraph();
    });

    this.kioskModeChecker();
  }

  public graphs: Graph[] = [];
  public currentIndex = -1;
  public currentGraph: Graph | null = null;

  public kioskMode: boolean = true;
  public interval: number = 15;
  subscription! : Subscription;

  public durations: Duration[] = [
    new Duration("5m", "5 minutes"),
    new Duration("1h", "1 hour"),
    new Duration("4h", "4 hours"),
    new Duration("1d", "1 day"),
    new Duration("2d", "2 day"),
    new Duration("7d", "1 week"),
    new Duration("30d", "1 month"),
    new Duration("365d", "1 year")
  ];
  public selectedDuration: Duration = this.durations.at(3)!;

  public visible: boolean = false;

  public kioskModeChecker() {
    if(this.kioskMode){
      this.activateKioskMode();
    } else if(!this.kioskMode){
      this.deactivateKioskMode();
    }
  }

  public activateKioskMode(): void {
    this.subscription = timer(0,this.interval * 1000).pipe(
      switchMap(() => {
        return this.nextGraph().name;
      })
    ).subscribe(res => console.log("switched to graph " + res));
  }

  public deactivateKioskMode(): void {
    this.subscription.unsubscribe();
  }

  public nextGraph(): Graph{
    this.currentIndex++;

    if(this.currentIndex >= this.graphs.length){
      this.currentIndex = 0;
    }

    return this.setCurrentGraphWithIndex(this.currentIndex);
  }

  public changeDuration(): void {
    const selectedDuration: string = this.selectedDuration.short;
    const durationPattern: RegExp = /from=now-\d+[a-z]/;

    this.graphs.forEach(graph => {
      graph.iFrameLink = graph.iFrameLink.replace(durationPattern, `from=now-${selectedDuration}`);
    });
  }


  public setCurrentGraphWithIndex(index: number): Graph {
    this.currentIndex = index;
    this.currentGraph = this.graphs[this.currentIndex];
    return this.currentGraph;
  }

  public getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }


  protected readonly console = console;
  protected readonly alert = alert;

  public toggleCollapse() {
    this.visible = !this.visible;
  }
}
