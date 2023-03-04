# Microservices With Kubernetes Template

A template distributed microservices system backed by Kubernetes and AWS.

## Languages Used

<p>
    <img src="https://skillicons.dev/icons?i=go,rust,ts,python,java" />
    <img src="https://img.icons8.com/color/48/000000/terraform.png" width="50" height="50" />
</p>

- **GoLang** - Writing a microservice | Writing end-to-end infrastructure tests using `terratest`
- **Rust** and **Java** - Used to build microservices
- **Typescript** - Making the frontend using `nextJS`
- **Python** - Writing Kubernetes end-to-end tests using `kubetest`
- **Terraform** - Defining local and remote `aws` infrastructure

## Objectives

These are the points I focused on while developing this project -

- Using the `hexagonal architecture` for developing softwares with minimum coupling between the components. The hexagonal architecture can be implemented both using object-oriented or functional paradigms.

- Understanding `object-oriented paradigms`. I used it while coding in GoLang and Java.

- Understanding `functional paradigms` (used while coding in Rust).

- Removing point of failures as much as possible. I have plans to use `event sourcing with CQRS` later which will ensure that event exchange between microservices is guaranteed if the business logic gets executed.