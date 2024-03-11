import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Graph} from "../model/Graph";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {HttpClient} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {interval, Subscription, timer} from "rxjs";
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-graph-overview',
  standalone: true,
  imports: [
    NgForOf,
    NgOptimizedImage,
    NgIf,
    FormsModule,
    MatSlideToggle
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

  public selectedRange: any;
  public ranges: string[] = ["1h", "4h", "1d", "1w", "1m", "1a"]

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

  public setCurrentGraphWithIndex(index: number): Graph {
    this.currentIndex = index;
    this.currentGraph = this.graphs[this.currentIndex];
    return this.currentGraph;
  }

  public getSafeUrl(url: string){
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  protected readonly console = console;
  protected readonly alert = alert;
}
