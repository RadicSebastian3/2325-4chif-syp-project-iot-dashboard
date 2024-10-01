import {Component, Input} from '@angular/core';
import {Graph} from "../model/Graph";
import {DomSanitizer} from "@angular/platform-browser";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-graph',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './graph.component.html',
  styleUrl: './graph.component.css'
})
export class GraphComponent {
  @Input() public graph: Graph | null = null;
  @Input() public visible: boolean = false;

  public constructor(public sanitizer: DomSanitizer) {

  }

  public getSafeUrl(url: string){
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
