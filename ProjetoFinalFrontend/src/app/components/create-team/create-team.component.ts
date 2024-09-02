import { Component, OnInit } from '@angular/core';
import { MenuLateralComponent } from "../menu-lateral/menu-lateral.component";
import { FooterComponent } from "../footer/footer.component";
import { MenuComponent } from "../menu/menu.component";
import { HeaderComponent } from "../header/header.component";
import { Team } from '../../models/team.model';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-team',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,MenuLateralComponent, FooterComponent, MenuComponent, HeaderComponent],
  templateUrl: './create-team.component.html',
  styleUrl: './create-team.component.scss'
})
export class CreateTeamComponent implements OnInit {
  timeForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.timeForm = this.fb.group({
      name: ['', Validators.required],
      address: [''],
      gym: [''],
      foundation: ['', Validators.required],
      emailContact: ['', [Validators.required, Validators.email]],
      phoneContact: ['']
    });
  }

  ngOnInit(): void { }

  criarTime(): void {
    if (this.timeForm.valid) {
      const novoTime = this.timeForm.value;
      console.log('Time criado:', novoTime);
      // Aqui você pode adicionar a lógica para enviar o time para o backend
    } else {
      console.log('Formulário inválido');
    }
  }
}
