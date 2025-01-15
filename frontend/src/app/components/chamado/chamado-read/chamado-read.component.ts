import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Chamado } from '../../../models/chamado';
import { ChamadoService } from '../../../services/chamado.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-chamado-read',
  standalone: true,
  imports: [
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatButtonModule,
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './chamado-read.component.html',
  styleUrl: './chamado-read.component.css'
})
export class ChamadoReadComponent implements OnInit{

  chamado: Chamado = {
    prioridade:  '',
    status:      '',
    titulo:      '',
    observacoes:   '',
    tecnico:     '',
    cliente:     '',
    nomeCliente: '',
    nomeTecnico: '',
  }

  constructor(
    private chamadoService: ChamadoService,
    private toastService: ToastrService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.chamado.id = this.route.snapshot.paramMap.get('id');
    this.findById();
  }

  findById(): void {
    this.chamadoService.findById(this.chamado.id).subscribe(resposta => {
      this.chamado = resposta;
    }, ex => {
      const errorMessage = ex?.error?.error || 'Erro ao buscar chamado';
      this.toastService.error(errorMessage);
    })
  }

  retornaStatus(status: any): string {
    if(status == '0'){
      return 'ABERTO'
    }else if(status == '1'){
      return 'EM ANDAMENTO'
    }else {
      return 'ENCERRADO'
    }
  }

  retornaPrioridade(prioridade: any): string {
    if(prioridade == '0'){
      return 'BAIXA'
    }else if(prioridade == '1'){
      return 'MEDIA'
    }else {
      return 'ALTA'
    }
  }


}
