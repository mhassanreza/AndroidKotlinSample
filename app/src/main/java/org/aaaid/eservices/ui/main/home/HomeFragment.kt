package org.aaaid.eservices.ui.main.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.aaaid.eservices.databinding.FragmentHomeBinding
import org.aaaid.eservices.enum.NetworkErrorType
import org.aaaid.eservices.network.NoNetworkConnectionContract
import org.aaaid.eservices.network.Resource
import org.aaaid.eservices.ui.dialogs.LoadingDialogFragment

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        PreferencesManager(activity).getString("ABC", "default value: Hassan Raza");
//        PreferencesManager(activity).setString("ABC", "Hassan Raza")
        callApiAndObserveForData()
    }

    private fun callApiAndObserveForData() {
        homeViewModel.demoData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    LoadingDialogFragment.showLoading(parentFragmentManager)
                }
                is Resource.Success -> {
                    LoadingDialogFragment.hideLoading()
                    // Update UI with list of items
                }
                is Resource.Error -> {
                    LoadingDialogFragment.hideLoading()
                    when (it.errorType) {
                        NetworkErrorType.NO_INTERNET -> {
                            noInternetConnectionLauncher.launch(null)
                        }
                        NetworkErrorType.UNAUTHORIZED -> {
                            Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                            // do the actions of logout
                        }
                        NetworkErrorType.SERVER_ERROR, NetworkErrorType.UNKNOWN, NetworkErrorType.TIMEOUT -> {
                            Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }
        homeViewModel.getUser(1399)
    }

    private val noInternetConnectionLauncher =
        registerForActivityResult(NoNetworkConnectionContract()) { result ->
            if (result) {
                homeViewModel.getUser(1399)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}