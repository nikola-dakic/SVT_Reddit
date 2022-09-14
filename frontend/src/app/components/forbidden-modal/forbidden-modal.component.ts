import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-forbidden-modal',
  templateUrl: './forbidden-modal.component.html',
  styleUrls: ['./forbidden-modal.component.css']
})
export class ForbiddenModalComponent implements OnInit {

  @Input()
  instantClick : boolean = false;
  @Input()
  stringMessage : string = "Only logged in users can access the page";

  @ViewChild('content',  {static: true}) content: any;

  constructor(private router: Router, config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  ngOnInit(): void {
    if (this.instantClick)
    {
      this.modalService.open(this.content);
    }
  }


  loginPage() {
    if (this.stringMessage == "Only logged in users can access the page")
    {
      this.router.navigateByUrl('/login');
    }

  }

}
