package com.example.todo_main.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_main.R
import com.example.todo_main.data.model.Task
import com.example.todo_main.ui.auth.AuthViewModel
import com.example.todo_main.ui.auth.ForgotPasswordFragment
import com.example.todo_main.util.UiState
import com.example.todo_main.util.snackbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlin.properties.ReadOnlyProperty

private val Any.text: Any
    get() {}
private val Any?.taskDescription: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any?.taskName: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any?.priorityDropdown: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any?.addTaskButton: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any?.locationTextField: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any?.dueDateDropdown: Any
    get() {
        TODO("Not yet implemented")
    }

@ForgotPasswordFragment.AndroidEntryPoint
class AddTaskFragment<FragmentAddTaskBinding>(private val task: Task? = null) : BottomSheetDialogFragment() {

    val TAG: String = "AddTaskFragment"
    lateinit var binding: FragmentAddTaskBinding
    private var closeFunction: ((Boolean) -> Unit)? = null
    private var saveTaskSuccess: Boolean = false
    private var isValid = true
    val viewModel: TaskViewModel by viewModels()

    private fun viewModels(): ReadOnlyProperty<AddTaskFragment<FragmentAddTaskBinding>, TaskViewModel> {
        TODO("Not yet implemented")
    }

    val authViewModel: AuthViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_add_task, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val also = FragmentAddTaskBinding.bind(view).also { binding = it }

            task?.let {
                binding.taskName.setText(it.title)
                binding.taskDescription.setText(it.description)
                binding.dueDateDropdown.setText(it.dueDate)
                binding.priorityDropdown.setText(it.priority)
                binding.locationTextField.setText(it.location)
            }

            val priorityArray = resources.getStringArray(R.array.priority)
            val arrayAdapter = activity?.let { ArrayAdapter(it, R.layout.dropdown_item, priorityArray) }
            binding.priorityDropdown.setAdapter(arrayAdapter)

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            binding.dueDateDropdown.setOnClickListener {
                datePicker.show(childFragmentManager, "DATE_PICKER")
            }
            datePicker.addOnPositiveButtonClickListener {
                binding.dueDateDropdown.setText(datePicker.headerText)
            }

            binding.addTaskButton.setOnClickListener {
                val title = binding.taskName.text.toString()
                val description = binding.taskDescription.text.toString()
                val priority = binding.priorityDropdown.text.toString()
                val date = binding.dueDateDropdown.text.toString()
                val location = binding.locationTextField.text.toString()
                val task = Task(title = title, description = description, priority = priority, dueDate = date, location = location)
                if (task.id == "") {
                    viewModel.addTask(task)
                }else {
                    viewModel.updateTask(task)
                }
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, HomeFragment())
                    ?.commit();
            }
            observer()
        }

    fun observer() {
        viewModel.addTask.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.addTaskButton.isEnabled = false
                } is UiState.Success -> {
                    saveTaskSuccess = true
                    snackbar("Task added successfully")
                    closeFunction?.invoke(true)
                    this.dismiss()
                } is UiState.Failure -> {
                    snackbar("Save failed, please try again")
                }
            }
        }

        viewModel.updateTask.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    false.also { binding.addTaskButton.isEnabled = it }
                } is UiState.Success -> {
                    saveTaskSuccess = true
                    snackbar("Task updated successfully")
                    closeFunction?.invoke(true)
                    this.dismiss()
                } is UiState.Failure -> {
                    snackbar("Save failed, please try again")
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        closeFunction?.invoke(saveTaskSuccess)
    }

    fun setDismissListener(function: ((Boolean) -> Unit)?) {
        closeFunction = function
    }
}

private fun Any.setAdapter(arrayAdapter: ArrayAdapter<String>?) {
    TODO("Not yet implemented")
}

private fun Any.setText(location: String) {
    TODO("Not yet implemented")
}

private fun Any.setOnClickListener(function: () -> Unit) {
    TODO("Not yet implemented")
}
